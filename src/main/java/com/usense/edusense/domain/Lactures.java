package com.usense.edusense.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Lactures.
 */
@Entity
@Table(name = "lactures")
public class Lactures implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_id")
    private Long classId;

    @Column(name = "time_from")
    private String timeFrom;

    @Column(name = "time_to")
    private String timeTo;

    @Column(name = "start_date")
    private LocalDate startDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClassId() {
        return classId;
    }

    public Lactures classId(Long classId) {
        this.classId = classId;
        return this;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public Lactures timeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
        return this;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public Lactures timeTo(String timeTo) {
        this.timeTo = timeTo;
        return this;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Lactures startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
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
        Lactures lactures = (Lactures) o;
        if (lactures.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lactures.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lactures{" +
            "id=" + getId() +
            ", classId=" + getClassId() +
            ", timeFrom='" + getTimeFrom() + "'" +
            ", timeTo='" + getTimeTo() + "'" +
            ", startDate='" + getStartDate() + "'" +
            "}";
    }
}
