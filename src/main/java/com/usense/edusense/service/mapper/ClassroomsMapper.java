package com.usense.edusense.service.mapper;

import com.usense.edusense.domain.*;
import com.usense.edusense.service.dto.ClassroomsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Classrooms and its DTO ClassroomsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClassroomsMapper extends EntityMapper<ClassroomsDTO, Classrooms> {



    default Classrooms fromId(Long id) {
        if (id == null) {
            return null;
        }
        Classrooms classrooms = new Classrooms();
        classrooms.setId(id);
        return classrooms;
    }
}
