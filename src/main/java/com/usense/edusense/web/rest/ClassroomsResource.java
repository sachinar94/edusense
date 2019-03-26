package com.usense.edusense.web.rest;
import com.usense.edusense.service.ClassroomsService;
import com.usense.edusense.web.rest.errors.BadRequestAlertException;
import com.usense.edusense.web.rest.util.HeaderUtil;
import com.usense.edusense.service.dto.ClassroomsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Classrooms.
 */
@RestController
@RequestMapping("/api")
public class ClassroomsResource {

    private final Logger log = LoggerFactory.getLogger(ClassroomsResource.class);

    private static final String ENTITY_NAME = "classrooms";

    private final ClassroomsService classroomsService;

    public ClassroomsResource(ClassroomsService classroomsService) {
        this.classroomsService = classroomsService;
    }

    /**
     * POST  /classrooms : Create a new classrooms.
     *
     * @param classroomsDTO the classroomsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classroomsDTO, or with status 400 (Bad Request) if the classrooms has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/classrooms")
    public ResponseEntity<ClassroomsDTO> createClassrooms(@RequestBody ClassroomsDTO classroomsDTO) throws URISyntaxException {
        log.debug("REST request to save Classrooms : {}", classroomsDTO);
        if (classroomsDTO.getId() != null) {
            throw new BadRequestAlertException("A new classrooms cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassroomsDTO result = classroomsService.save(classroomsDTO);
        return ResponseEntity.created(new URI("/api/classrooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /classrooms : Updates an existing classrooms.
     *
     * @param classroomsDTO the classroomsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classroomsDTO,
     * or with status 400 (Bad Request) if the classroomsDTO is not valid,
     * or with status 500 (Internal Server Error) if the classroomsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/classrooms")
    public ResponseEntity<ClassroomsDTO> updateClassrooms(@RequestBody ClassroomsDTO classroomsDTO) throws URISyntaxException {
        log.debug("REST request to update Classrooms : {}", classroomsDTO);
        if (classroomsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClassroomsDTO result = classroomsService.save(classroomsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classroomsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /classrooms : get all the classrooms.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of classrooms in body
     */
    @GetMapping("/classrooms")
    public List<ClassroomsDTO> getAllClassrooms() {
        log.debug("REST request to get all Classrooms");
        return classroomsService.findAll();
    }

    /**
     * GET  /classrooms/:id : get the "id" classrooms.
     *
     * @param id the id of the classroomsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classroomsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/classrooms/{id}")
    public ResponseEntity<ClassroomsDTO> getClassrooms(@PathVariable Long id) {
        log.debug("REST request to get Classrooms : {}", id);
        Optional<ClassroomsDTO> classroomsDTO = classroomsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classroomsDTO);
    }

    /**
     * DELETE  /classrooms/:id : delete the "id" classrooms.
     *
     * @param id the id of the classroomsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/classrooms/{id}")
    public ResponseEntity<Void> deleteClassrooms(@PathVariable Long id) {
        log.debug("REST request to delete Classrooms : {}", id);
        classroomsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
