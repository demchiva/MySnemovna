package cz.my.snemovna.jpa.model.members;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

/**
 * The entity represents additional information about person.
 * Typically, it is information about relation to external systems (e.g. membership in Senat.)
 */
@Entity
@Table(name = "person_extra")
@Data
public class PersonExtra implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "organ_id")
    private Long organId;

    @Column(name = "type")
    private Integer type;

    @Column(name = "district")
    private Long district;

    @Column(name = "party")
    private String party;

    @Column(name = "external_id")
    private Long externalId;
}
