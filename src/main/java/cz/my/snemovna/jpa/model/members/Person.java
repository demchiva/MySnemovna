package cz.my.snemovna.jpa.model.members;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "person")
@Data
public class Person implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "title_before")
    private String titleBeforeName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "title_after")
    private String titleAfterName;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "sex", length = 1)
    private String sex;

    @Column(name = "updatedAt")
    private LocalDate updatedAt;

    @Column(name = "date_of_death")
    private LocalDate dateOfDeath;
}
