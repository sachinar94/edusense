package com.usense.edusense.service.mapper;

import com.usense.edusense.domain.*;
import com.usense.edusense.service.dto.LacturesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Lactures and its DTO LacturesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LacturesMapper extends EntityMapper<LacturesDTO, Lactures> {



    default Lactures fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lactures lactures = new Lactures();
        lactures.setId(id);
        return lactures;
    }
}
