package cz.my.snemovna.jpa.model.members;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The entity represents person enrollment (organ or function in parliament).
 * One person can have more than one enrollment (1:n).
 */
@Entity
@Table(name = "enrollment")
@Data
public class Enrollment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "id_of")
    private Long membershipOrFunctionId;

    @Column(name = "type")
    private Integer membershipOrFunction;

    @Column(name = "enrollment_from")
    private String enrollmentFrom;

    @Column(name = "enrollment_to")
    private String enrollmentTo;

    @Column(name = "mandate_from")
    private LocalDate mandateFrom;

    @Column(name = "mandate_to")
    private LocalDate mandateTo;
}
