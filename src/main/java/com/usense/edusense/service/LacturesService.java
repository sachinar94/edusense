package com.usense.edusense.service;

import com.usense.edusense.service.dto.LacturesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Lactures.
 */
public interface LacturesService {

    /**
     * Save a lactures.
     *
     * @param lacturesDTO the entity to save
     * @return the persisted entity
     */
    LacturesDTO save(LacturesDTO lacturesDTO);

    /**
     * Get all the lactures.
     *
     * @return the list of entities
     */
    List<LacturesDTO> findAll();


    /**
     * Get the "id" lactures.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LacturesDTO> findOne(Long id);

    /**
     * Delete the "id" lactures.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
