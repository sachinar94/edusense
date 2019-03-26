package com.usense.edusense.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Classrooms entity.
 */
public class ClassroomsDTO implements Serializable {

    private Long id;

    private String name;

    private String lactureHall;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLactureHall() {
        return lactureHall;
    }

    public void setLactureHall(String lactureHall) {
        this.lactureHall = lactureHall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClassroomsDTO classroomsDTO = (ClassroomsDTO) o;
        if (classroomsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classroomsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassroomsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lactureHall='" + getLactureHall() + "'" +
            "}";
    }
}
