package fr.univrouen.cv24.entities.skills;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "skills")
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable()
    @Nullable
    private List<Diploma> diplomas = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable()
    @Nullable
    private List<Certification> certifications = new ArrayList<>();

    @Nullable
    public List<Diploma> getDiplomas() {
        return diplomas;
    }

    public void setDiplomas(@Nullable List<Diploma> diplomas) {
        this.diplomas = diplomas;
    }

    @Nullable
    public List<Certification> getCertifications() {
        return certifications;
    }

    public void setCertifications(@Nullable List<Certification> certifications) {
        this.certifications = certifications;
    }

    public Long getId() {
        return id;
    }
}
