package fr.univrouen.cv24.entities;

import fr.univrouen.cv24.entities.miscellaneous.Miscellaneous;
import fr.univrouen.cv24.entities.skills.Skills;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cv")
public class CV {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Lob
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    @OneToOne
//    private Identity identity;
//
//    @OneToOne
//    private Goal goal;
//
//    @OneToMany
//    @JoinColumn()
//    private List<ProfessionalExperiences> professionalExperiences;
//
//    @OneToOne
//    private Skills skills;
//
//    @OneToOne
//    @Nullable
//    private Miscellaneous miscellaneous;
//
//    public Long getId() {
//        return id;
//    }
//
//    public Identity getIdentity() {
//        return identity;
//    }
//
//    public void setIdentity(Identity identity) {
//        this.identity = identity;
//    }
//
//    public Goal getGoal() {
//        return goal;
//    }
//
//    public void setGoal(Goal goal) {
//        this.goal = goal;
//    }
//
//    public List<ProfessionalExperiences> getProfessionalExperiences() {
//        return professionalExperiences;
//    }
//
//    public void setProfessionalExperiences(List<ProfessionalExperiences> professionalExperiences) {
//        this.professionalExperiences = professionalExperiences;
//    }
//
//    public Skills getSkills() {
//        return skills;
//    }
//
//    public void setSkills(Skills skills) {
//        this.skills = skills;
//    }
//
//    @Nullable
//    public Miscellaneous getMiscellaneous() {
//        return miscellaneous;
//    }
//
//    public void setMiscellaneous(@Nullable Miscellaneous miscellaneous) {
//        this.miscellaneous = miscellaneous;
//    }
}
