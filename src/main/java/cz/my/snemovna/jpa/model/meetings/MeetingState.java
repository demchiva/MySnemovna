package cz.my.snemovna.jpa.model.meetings;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "meeting_state")
@Data
public class MeetingState implements Serializable {

    @Id
    @Column(name = "meeting_id")
    private Long meetingId;

    @Column(name = "state")
    private Integer state;

    @Column(name = "type")
    private Integer type;

    @Column(name = "meeting_begin_text")
    private String meetingBeginText;

    @Column(name = "meeting_status_text")
    private String meetingStatusText;

    @Column(name = "meeting_status_text_2")
    private String meetingStatusText2;
}
