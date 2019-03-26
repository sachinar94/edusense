package com.usense.edusense.service.impl;

import com.usense.edusense.service.LacturesService;
import com.usense.edusense.domain.Lactures;
import com.usense.edusense.repository.LacturesRepository;
import com.usense.edusense.service.dto.LacturesDTO;
import com.usense.edusense.service.mapper.LacturesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Lactures.
 */
@Service
@Transactional
public class LacturesServiceImpl implements LacturesService {

    private final Logger log = LoggerFactory.getLogger(LacturesServiceImpl.class);

    private final LacturesRepository lacturesRepository;

    private final LacturesMapper lacturesMapper;

    public LacturesServiceImpl(LacturesRepository lacturesRepository, LacturesMapper lacturesMapper) {
        this.lacturesRepository = lacturesRepository;
        this.lacturesMapper = lacturesMapper;
    }

    /**
     * Save a lactures.
     *
     * @param lacturesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LacturesDTO save(LacturesDTO lacturesDTO) {
        log.debug("Request to save Lactures : {}", lacturesDTO);
        Lactures lactures = lacturesMapper.toEntity(lacturesDTO);
        lactures = lacturesRepository.save(lactures);
        return lacturesMapper.toDto(lactures);
    }

    /**
     * Get all the lactures.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LacturesDTO> findAll() {
        log.debug("Request to get all Lactures");
        return lacturesRepository.findAll().stream()
            .map(lacturesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one lactures by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LacturesDTO> findOne(Long id) {
        log.debug("Request to get Lactures : {}", id);
        return lacturesRepository.findById(id)
            .map(lacturesMapper::toDto);
    }

    /**
     * Delete the lactures by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lactures : {}", id);
        lacturesRepository.deleteById(id);
    }
}
