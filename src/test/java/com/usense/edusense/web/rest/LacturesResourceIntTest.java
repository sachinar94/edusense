package com.usense.edusense.web.rest;

import com.usense.edusense.EdusenseApp;

import com.usense.edusense.domain.Lactures;
import com.usense.edusense.repository.LacturesRepository;
import com.usense.edusense.service.LacturesService;
import com.usense.edusense.service.dto.LacturesDTO;
import com.usense.edusense.service.mapper.LacturesMapper;
import com.usense.edusense.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.usense.edusense.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LacturesResource REST controller.
 *
 * @see LacturesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdusenseApp.class)
public class LacturesResourceIntTest {

    private static final Long DEFAULT_CLASS_ID = 1L;
    private static final Long UPDATED_CLASS_ID = 2L;

    private static final String DEFAULT_TIME_FROM = "AAAAAAAAAA";
    private static final String UPDATED_TIME_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_TIME_TO = "AAAAAAAAAA";
    private static final String UPDATED_TIME_TO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private LacturesRepository lacturesRepository;

    @Autowired
    private LacturesMapper lacturesMapper;

    @Autowired
    private LacturesService lacturesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restLacturesMockMvc;

    private Lactures lactures;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LacturesResource lacturesResource = new LacturesResource(lacturesService);
        this.restLacturesMockMvc = MockMvcBuilders.standaloneSetup(lacturesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lactures createEntity(EntityManager em) {
        Lactures lactures = new Lactures()
            .classId(DEFAULT_CLASS_ID)
            .timeFrom(DEFAULT_TIME_FROM)
            .timeTo(DEFAULT_TIME_TO)
            .startDate(DEFAULT_START_DATE);
        return lactures;
    }

    @Before
    public void initTest() {
        lactures = createEntity(em);
    }

    @Test
    @Transactional
    public void createLactures() throws Exception {
        int databaseSizeBeforeCreate = lacturesRepository.findAll().size();

        // Create the Lactures
        LacturesDTO lacturesDTO = lacturesMapper.toDto(lactures);
        restLacturesMockMvc.perform(post("/api/lactures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lacturesDTO)))
            .andExpect(status().isCreated());

        // Validate the Lactures in the database
        List<Lactures> lacturesList = lacturesRepository.findAll();
        assertThat(lacturesList).hasSize(databaseSizeBeforeCreate + 1);
        Lactures testLactures = lacturesList.get(lacturesList.size() - 1);
        assertThat(testLactures.getClassId()).isEqualTo(DEFAULT_CLASS_ID);
        assertThat(testLactures.getTimeFrom()).isEqualTo(DEFAULT_TIME_FROM);
        assertThat(testLactures.getTimeTo()).isEqualTo(DEFAULT_TIME_TO);
        assertThat(testLactures.getStartDate()).isEqualTo(DEFAULT_START_DATE);
    }

    @Test
    @Transactional
    public void createLacturesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lacturesRepository.findAll().size();

        // Create the Lactures with an existing ID
        lactures.setId(1L);
        LacturesDTO lacturesDTO = lacturesMapper.toDto(lactures);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLacturesMockMvc.perform(post("/api/lactures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lacturesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lactures in the database
        List<Lactures> lacturesList = lacturesRepository.findAll();
        assertThat(lacturesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLactures() throws Exception {
        // Initialize the database
        lacturesRepository.saveAndFlush(lactures);

        // Get all the lacturesList
        restLacturesMockMvc.perform(get("/api/lactures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lactures.getId().intValue())))
            .andExpect(jsonPath("$.[*].classId").value(hasItem(DEFAULT_CLASS_ID.intValue())))
            .andExpect(jsonPath("$.[*].timeFrom").value(hasItem(DEFAULT_TIME_FROM.toString())))
            .andExpect(jsonPath("$.[*].timeTo").value(hasItem(DEFAULT_TIME_TO.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getLactures() throws Exception {
        // Initialize the database
        lacturesRepository.saveAndFlush(lactures);

        // Get the lactures
        restLacturesMockMvc.perform(get("/api/lactures/{id}", lactures.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lactures.getId().intValue()))
            .andExpect(jsonPath("$.classId").value(DEFAULT_CLASS_ID.intValue()))
            .andExpect(jsonPath("$.timeFrom").value(DEFAULT_TIME_FROM.toString()))
            .andExpect(jsonPath("$.timeTo").value(DEFAULT_TIME_TO.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLactures() throws Exception {
        // Get the lactures
        restLacturesMockMvc.perform(get("/api/lactures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLactures() throws Exception {
        // Initialize the database
        lacturesRepository.saveAndFlush(lactures);

        int databaseSizeBeforeUpdate = lacturesRepository.findAll().size();

        // Update the lactures
        Lactures updatedLactures = lacturesRepository.findById(lactures.getId()).get();
        // Disconnect from session so that the updates on updatedLactures are not directly saved in db
        em.detach(updatedLactures);
        updatedLactures
            .classId(UPDATED_CLASS_ID)
            .timeFrom(UPDATED_TIME_FROM)
            .timeTo(UPDATED_TIME_TO)
            .startDate(UPDATED_START_DATE);
        LacturesDTO lacturesDTO = lacturesMapper.toDto(updatedLactures);

        restLacturesMockMvc.perform(put("/api/lactures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lacturesDTO)))
            .andExpect(status().isOk());

        // Validate the Lactures in the database
        List<Lactures> lacturesList = lacturesRepository.findAll();
        assertThat(lacturesList).hasSize(databaseSizeBeforeUpdate);
        Lactures testLactures = lacturesList.get(lacturesList.size() - 1);
        assertThat(testLactures.getClassId()).isEqualTo(UPDATED_CLASS_ID);
        assertThat(testLactures.getTimeFrom()).isEqualTo(UPDATED_TIME_FROM);
        assertThat(testLactures.getTimeTo()).isEqualTo(UPDATED_TIME_TO);
        assertThat(testLactures.getStartDate()).isEqualTo(UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingLactures() throws Exception {
        int databaseSizeBeforeUpdate = lacturesRepository.findAll().size();

        // Create the Lactures
        LacturesDTO lacturesDTO = lacturesMapper.toDto(lactures);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLacturesMockMvc.perform(put("/api/lactures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lacturesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lactures in the database
        List<Lactures> lacturesList = lacturesRepository.findAll();
        assertThat(lacturesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLactures() throws Exception {
        // Initialize the database
        lacturesRepository.saveAndFlush(lactures);

        int databaseSizeBeforeDelete = lacturesRepository.findAll().size();

        // Delete the lactures
        restLacturesMockMvc.perform(delete("/api/lactures/{id}", lactures.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lactures> lacturesList = lacturesRepository.findAll();
        assertThat(lacturesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lactures.class);
        Lactures lactures1 = new Lactures();
        lactures1.setId(1L);
        Lactures lactures2 = new Lactures();
        lactures2.setId(lactures1.getId());
        assertThat(lactures1).isEqualTo(lactures2);
        lactures2.setId(2L);
        assertThat(lactures1).isNotEqualTo(lactures2);
        lactures1.setId(null);
        assertThat(lactures1).isNotEqualTo(lactures2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LacturesDTO.class);
        LacturesDTO lacturesDTO1 = new LacturesDTO();
        lacturesDTO1.setId(1L);
        LacturesDTO lacturesDTO2 = new LacturesDTO();
        assertThat(lacturesDTO1).isNotEqualTo(lacturesDTO2);
        lacturesDTO2.setId(lacturesDTO1.getId());
        assertThat(lacturesDTO1).isEqualTo(lacturesDTO2);
        lacturesDTO2.setId(2L);
        assertThat(lacturesDTO1).isNotEqualTo(lacturesDTO2);
        lacturesDTO1.setId(null);
        assertThat(lacturesDTO1).isNotEqualTo(lacturesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lacturesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lacturesMapper.fromId(null)).isNull();
    }
}
