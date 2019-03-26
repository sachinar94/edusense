package com.usense.edusense.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Classrooms.
 */
@Entity
@Table(name = "classrooms")
public class Classrooms implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lacture_hall")
    private String lactureHall;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Classrooms name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLactureHall() {
        return lactureHall;
    }

    public Classrooms lactureHall(String lactureHall) {
        this.lactureHall = lactureHall;
        return this;
    }

    public void setLactureHall(String lactureHall) {
        this.lactureHall = lactureHall;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Classrooms classrooms = (Classrooms) o;
        if (classrooms.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classrooms.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Classrooms{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lactureHall='" + getLactureHall() + "'" +
            "}";
    }
}
