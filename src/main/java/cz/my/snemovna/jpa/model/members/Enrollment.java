package cz.my.snemovna.jpa.model.members;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollment")
@Data
public class Enrollment implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "id_of")
    private Long membershipOrFunctionId;

    @Column(name = "type")
    private Integer membershipOrFunction;

    @Column(name = "enrollment_from")
    private LocalDateTime enrollmentFrom;

    @Column(name = "enrollment_to")
    private LocalDateTime enrollmentTo;

    @Column(name = "mandate_from")
    private LocalDate mandateFrom;

    @Column(name = "mandate_to")
    private LocalDate mandateTo;
}
