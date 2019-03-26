package com.usense.edusense.service;

import com.usense.edusense.service.dto.ClassroomsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Classrooms.
 */
public interface ClassroomsService {

    /**
     * Save a classrooms.
     *
     * @param classroomsDTO the entity to save
     * @return the persisted entity
     */
    ClassroomsDTO save(ClassroomsDTO classroomsDTO);

    /**
     * Get all the classrooms.
     *
     * @return the list of entities
     */
    List<ClassroomsDTO> findAll();


    /**
     * Get the "id" classrooms.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ClassroomsDTO> findOne(Long id);

    /**
     * Delete the "id" classrooms.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
