package fr.univrouen.cv24.entities.miscellaneous;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "miscellaneous")
public class Miscellaneous {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "miscellaneous_living_language")
    private List<LivingLanguage> livingLanguages = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "miscellaneous_further_information")
    @Nullable
    private List<FurtherInformation> other = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public List<LivingLanguage> getLivingLanguages() {
        return livingLanguages;
    }

    public void setLivingLanguages(List<LivingLanguage> livingLanguages) {
        this.livingLanguages = livingLanguages;
    }

    @Nullable
    public List<FurtherInformation> getOther() {
        return other;
    }

    public void setOther(@Nullable List<FurtherInformation> other) {
        this.other = other;
    }
}
