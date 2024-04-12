package fr.univrouen.cv24.entities.miscellaneous;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "further_information")
public class FurtherInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 32)
    private String title;

    @Nullable
    @Size(max = 128)
    private String comment;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Nullable
    public String getComment() {
        return comment;
    }

    public void setComment(@Nullable String comment) {
        this.comment = comment;
    }
}
