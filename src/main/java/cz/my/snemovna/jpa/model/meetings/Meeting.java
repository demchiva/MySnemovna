package cz.my.snemovna.jpa.model.meetings;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "meeting")
@Data
public class Meeting implements Serializable {

    @EmbeddedId
    private MeetingAgendaId id;

    @Column(name = "organ_id")
    private Long organId;

    @Column(name = "meeting_number")
    private Long meetingNumber;

    @Column(name = "date_from")
    private LocalDateTime dateFrom;

    @Column(name = "date_to")
    private LocalDateTime dateTo;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
