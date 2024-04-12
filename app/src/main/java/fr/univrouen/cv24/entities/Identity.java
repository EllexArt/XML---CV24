package fr.univrouen.cv24.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "identity")
public class Identity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 32)
    private String name;

    @Size(max = 32)
    private String firstName;

    @Size(max = 50)
    private String gender;

    @Nullable
    @Size(max = 255)
    private String email;

    @Nullable
    @Size(max = 255)
    private String phone;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    public void setPhone(@Nullable String phone) {
        this.phone = phone;
    }
}
