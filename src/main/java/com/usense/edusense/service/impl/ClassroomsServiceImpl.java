package com.usense.edusense.service.impl;

import com.usense.edusense.service.ClassroomsService;
import com.usense.edusense.domain.Classrooms;
import com.usense.edusense.repository.ClassroomsRepository;
import com.usense.edusense.service.dto.ClassroomsDTO;
import com.usense.edusense.service.mapper.ClassroomsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Classrooms.
 */
@Service
@Transactional
public class ClassroomsServiceImpl implements ClassroomsService {

    private final Logger log = LoggerFactory.getLogger(ClassroomsServiceImpl.class);

    private final ClassroomsRepository classroomsRepository;

    private final ClassroomsMapper classroomsMapper;

    public ClassroomsServiceImpl(ClassroomsRepository classroomsRepository, ClassroomsMapper classroomsMapper) {
        this.classroomsRepository = classroomsRepository;
        this.classroomsMapper = classroomsMapper;
    }

    /**
     * Save a classrooms.
     *
     * @param classroomsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClassroomsDTO save(ClassroomsDTO classroomsDTO) {
        log.debug("Request to save Classrooms : {}", classroomsDTO);
        Classrooms classrooms = classroomsMapper.toEntity(classroomsDTO);
        classrooms = classroomsRepository.save(classrooms);
        return classroomsMapper.toDto(classrooms);
    }

    /**
     * Get all the classrooms.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ClassroomsDTO> findAll() {
        log.debug("Request to get all Classrooms");
        return classroomsRepository.findAll().stream()
            .map(classroomsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one classrooms by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClassroomsDTO> findOne(Long id) {
        log.debug("Request to get Classrooms : {}", id);
        return classroomsRepository.findById(id)
            .map(classroomsMapper::toDto);
    }

    /**
     * Delete the classrooms by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Classrooms : {}", id);
        classroomsRepository.deleteById(id);
    }
}
