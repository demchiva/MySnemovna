package cz.my.snemovna.jpa.model.meetings;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "meeting_point")
@Data
public class MeetingPoint implements Serializable {

    @EmbeddedId
    private MeetingAgendaId id;

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
