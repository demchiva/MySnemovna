package cz.my.snemovna.jpa.model.members;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

/**
 * The entity represents functions in parliament organs.
 * Function can be used in several enrollments (n:1).
 * One enrollment can have only one function.
 */
@Entity
@Table(name = "function")
@Data
public class Function implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "organ_id")
    private Long organId;

    @Column(name = "function_type_id")
    private Long functionTypeId;

    @Column(name = "name_cz")
    private String nameCz;

    @Column(name = "priority")
    private Integer priority;
}
