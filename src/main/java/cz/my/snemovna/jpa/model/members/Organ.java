package cz.my.snemovna.jpa.model.members;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "organ")
@Data
public class Organ implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "organ_type_id")
    private Long organTypeId;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "name_cz", length = 500)
    private String nameCz;

    @Column(name = "name_en", length = 500)
    private String nameEn;

    @Column(name = "date_from")
    private LocalDate dateFrom;

    @Column(name = "date_to")
    private LocalDate dateTo;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "cl_organ_base")
    private Integer clOrganBase;
}
