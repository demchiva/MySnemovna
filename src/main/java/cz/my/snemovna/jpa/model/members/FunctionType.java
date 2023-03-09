package cz.my.snemovna.jpa.model.members;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "function_type")
@Data
public class FunctionType implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "organ_type_id")
    private Long organTypeId;

    @Column(name = "name_cz")
    private String nameCz;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "common_type")
    private Long commonType;
}
