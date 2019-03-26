package com.usense.edusense.repository;

import com.usense.edusense.domain.Lactures;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Lactures entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LacturesRepository extends JpaRepository<Lactures, Long> {

}
