package com.usense.edusense.web.rest;

import com.usense.edusense.EdusenseApp;

import com.usense.edusense.domain.Classrooms;
import com.usense.edusense.repository.ClassroomsRepository;
import com.usense.edusense.service.ClassroomsService;
import com.usense.edusense.service.dto.ClassroomsDTO;
import com.usense.edusense.service.mapper.ClassroomsMapper;
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
import java.util.List;


import static com.usense.edusense.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClassroomsResource REST controller.
 *
 * @see ClassroomsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdusenseApp.class)
public class ClassroomsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LACTURE_HALL = "AAAAAAAAAA";
    private static final String UPDATED_LACTURE_HALL = "BBBBBBBBBB";

    @Autowired
    private ClassroomsRepository classroomsRepository;

    @Autowired
    private ClassroomsMapper classroomsMapper;

    @Autowired
    private ClassroomsService classroomsService;

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

    private MockMvc restClassroomsMockMvc;

    private Classrooms classrooms;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassroomsResource classroomsResource = new ClassroomsResource(classroomsService);
        this.restClassroomsMockMvc = MockMvcBuilders.standaloneSetup(classroomsResource)
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
    public static Classrooms createEntity(EntityManager em) {
        Classrooms classrooms = new Classrooms()
            .name(DEFAULT_NAME)
            .lactureHall(DEFAULT_LACTURE_HALL);
        return classrooms;
    }

    @Before
    public void initTest() {
        classrooms = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassrooms() throws Exception {
        int databaseSizeBeforeCreate = classroomsRepository.findAll().size();

        // Create the Classrooms
        ClassroomsDTO classroomsDTO = classroomsMapper.toDto(classrooms);
        restClassroomsMockMvc.perform(post("/api/classrooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classroomsDTO)))
            .andExpect(status().isCreated());

        // Validate the Classrooms in the database
        List<Classrooms> classroomsList = classroomsRepository.findAll();
        assertThat(classroomsList).hasSize(databaseSizeBeforeCreate + 1);
        Classrooms testClassrooms = classroomsList.get(classroomsList.size() - 1);
        assertThat(testClassrooms.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClassrooms.getLactureHall()).isEqualTo(DEFAULT_LACTURE_HALL);
    }

    @Test
    @Transactional
    public void createClassroomsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classroomsRepository.findAll().size();

        // Create the Classrooms with an existing ID
        classrooms.setId(1L);
        ClassroomsDTO classroomsDTO = classroomsMapper.toDto(classrooms);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassroomsMockMvc.perform(post("/api/classrooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classroomsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Classrooms in the database
        List<Classrooms> classroomsList = classroomsRepository.findAll();
        assertThat(classroomsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClassrooms() throws Exception {
        // Initialize the database
        classroomsRepository.saveAndFlush(classrooms);

        // Get all the classroomsList
        restClassroomsMockMvc.perform(get("/api/classrooms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classrooms.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].lactureHall").value(hasItem(DEFAULT_LACTURE_HALL.toString())));
    }
    
    @Test
    @Transactional
    public void getClassrooms() throws Exception {
        // Initialize the database
        classroomsRepository.saveAndFlush(classrooms);

        // Get the classrooms
        restClassroomsMockMvc.perform(get("/api/classrooms/{id}", classrooms.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classrooms.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.lactureHall").value(DEFAULT_LACTURE_HALL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClassrooms() throws Exception {
        // Get the classrooms
        restClassroomsMockMvc.perform(get("/api/classrooms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassrooms() throws Exception {
        // Initialize the database
        classroomsRepository.saveAndFlush(classrooms);

        int databaseSizeBeforeUpdate = classroomsRepository.findAll().size();

        // Update the classrooms
        Classrooms updatedClassrooms = classroomsRepository.findById(classrooms.getId()).get();
        // Disconnect from session so that the updates on updatedClassrooms are not directly saved in db
        em.detach(updatedClassrooms);
        updatedClassrooms
            .name(UPDATED_NAME)
            .lactureHall(UPDATED_LACTURE_HALL);
        ClassroomsDTO classroomsDTO = classroomsMapper.toDto(updatedClassrooms);

        restClassroomsMockMvc.perform(put("/api/classrooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classroomsDTO)))
            .andExpect(status().isOk());

        // Validate the Classrooms in the database
        List<Classrooms> classroomsList = classroomsRepository.findAll();
        assertThat(classroomsList).hasSize(databaseSizeBeforeUpdate);
        Classrooms testClassrooms = classroomsList.get(classroomsList.size() - 1);
        assertThat(testClassrooms.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClassrooms.getLactureHall()).isEqualTo(UPDATED_LACTURE_HALL);
    }

    @Test
    @Transactional
    public void updateNonExistingClassrooms() throws Exception {
        int databaseSizeBeforeUpdate = classroomsRepository.findAll().size();

        // Create the Classrooms
        ClassroomsDTO classroomsDTO = classroomsMapper.toDto(classrooms);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassroomsMockMvc.perform(put("/api/classrooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classroomsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Classrooms in the database
        List<Classrooms> classroomsList = classroomsRepository.findAll();
        assertThat(classroomsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClassrooms() throws Exception {
        // Initialize the database
        classroomsRepository.saveAndFlush(classrooms);

        int databaseSizeBeforeDelete = classroomsRepository.findAll().size();

        // Delete the classrooms
        restClassroomsMockMvc.perform(delete("/api/classrooms/{id}", classrooms.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Classrooms> classroomsList = classroomsRepository.findAll();
        assertThat(classroomsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Classrooms.class);
        Classrooms classrooms1 = new Classrooms();
        classrooms1.setId(1L);
        Classrooms classrooms2 = new Classrooms();
        classrooms2.setId(classrooms1.getId());
        assertThat(classrooms1).isEqualTo(classrooms2);
        classrooms2.setId(2L);
        assertThat(classrooms1).isNotEqualTo(classrooms2);
        classrooms1.setId(null);
        assertThat(classrooms1).isNotEqualTo(classrooms2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassroomsDTO.class);
        ClassroomsDTO classroomsDTO1 = new ClassroomsDTO();
        classroomsDTO1.setId(1L);
        ClassroomsDTO classroomsDTO2 = new ClassroomsDTO();
        assertThat(classroomsDTO1).isNotEqualTo(classroomsDTO2);
        classroomsDTO2.setId(classroomsDTO1.getId());
        assertThat(classroomsDTO1).isEqualTo(classroomsDTO2);
        classroomsDTO2.setId(2L);
        assertThat(classroomsDTO1).isNotEqualTo(classroomsDTO2);
        classroomsDTO1.setId(null);
        assertThat(classroomsDTO1).isNotEqualTo(classroomsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(classroomsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(classroomsMapper.fromId(null)).isNull();
    }
}
