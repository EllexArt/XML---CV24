package fr.univrouen.cv24.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.sql.Date;

@Entity
@Table(name = "professional_experiences")
public class ProfessionalExperiences {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date beginDate;

    @Nullable
    private Date endDate;

    @Size(max = 128)
    private String title;

    public Long getId() {
        return id;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @Nullable
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(@Nullable Date endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
