package cz.my.snemovna.jpa.model.members;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "enrollment")
@Data
public class Enrollment implements Serializable {

    @EmbeddedId
    private EnrollmentId id;

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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public class EnrollmentId implements Serializable {

        @Column(name = "person_id")
        private Long personId;

        @Column(name = "id_of")
        private Long membershipOrFunctionId;
    }
}
