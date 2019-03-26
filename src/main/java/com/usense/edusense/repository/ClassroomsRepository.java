package com.usense.edusense.repository;

import com.usense.edusense.domain.Classrooms;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Classrooms entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassroomsRepository extends JpaRepository<Classrooms, Long> {

}
