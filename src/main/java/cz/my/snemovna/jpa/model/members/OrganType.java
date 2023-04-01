package cz.my.snemovna.jpa.model.members;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

/**
 * The entity represents all possible organ types.
 * Organ can have only one type.
 * Same type can have more than one organ (n:1).
 */
@Entity
@Table(name = "organ_type")
@Data
public class OrganType implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "name_cz")
    private String nameCz;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "common_type")
    private Long commonType;

    @Column(name = "priority")
    private Integer priority;
}
