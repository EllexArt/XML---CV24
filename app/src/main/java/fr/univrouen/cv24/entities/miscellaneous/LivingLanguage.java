package fr.univrouen.cv24.entities.miscellaneous;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "living_language")
public class LivingLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min = 2, max = 2)
    private String language;
    @Size(max = 10)
    private String certification;
    @Nullable
    @Size(min = 2, max = 2)
    private String nivs;
    @Nullable
    private Integer nivi;

    public Long getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    @Nullable
    public String getNivs() {
        return nivs;
    }

    public void setNivs(@Nullable String nivs) {
        this.nivs = nivs;
    }

    @Nullable
    public Integer getNivi() {
        return nivi;
    }

    public void setNivi(@Nullable Integer nivi) {
        this.nivi = nivi;
    }
}
