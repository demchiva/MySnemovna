package cz.my.snemovna.jpa.model.meetings;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

/**
 * The entity class represents one meeting point to discuss in one Meeting.
 * Typically, one meeting has more than one point (1:n).
 */
@Entity
@Table(name = "meeting_point")
@Data
public class MeetingPoint implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "point_id")
    private Long pointId;

    @Column(name = "agenda_type")
    private Integer agendaType;

    @Column(name = "meeting_id")
    private Long meetingId;

    @Column(name = "print_id")
    private Long printId;

    @Column(name = "type_id")
    private Integer typeId;

    @Column(name = "point_number")
    private Long pointNumber;

    @Column(name = "full_name", length = 2000)
    private String fullName;

    @Column(name = "name_suffix")
    private String nameSuffix;

    @Column(name = "note")
    private String note;

    @Column(name = "state_id")
    private Long stateId;

    @Column(name = "mode")
    private Long mode;

    @Column(name = "short_note")
    private String shortNote;

    @Column(name = "type")
    private Integer type;

    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "short_name")
    private String shortName;
}
