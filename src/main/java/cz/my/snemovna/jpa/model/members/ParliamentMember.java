package cz.my.snemovna.jpa.model.members;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "member")
@Data
public class ParliamentMember implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "region_id")
    private Long regionId;

    @Column(name = "party_id")
    private Long partyId;

    @Column(name = "period_id")
    private Long periodId;

    @Column(name = "web")
    private String web;

    @Column(name = "street")
    private String street;

    @Column(name = "municipality")
    private String municipality;

    @Column(name = "zip")
    private String zip;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "psp_phone")
    private String pspPhone;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "photo")
    private boolean photo;
}
