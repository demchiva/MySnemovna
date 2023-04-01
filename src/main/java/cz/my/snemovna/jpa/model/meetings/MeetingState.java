package cz.my.snemovna.jpa.model.meetings;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

/**
 * The entity class represents meeting state.
 * Meeting can have max 1 meeting state, but also can have no state in this table (1:0..1).
 */
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

    /**
     * Information that overrides date and time of meeting start and end.
     */
    @Column(name = "meeting_begin_text")
    private String meetingBeginText;

    /**
     * Information about meeting interruption.
     */
    @Column(name = "meeting_status_text")
    private String meetingStatusText;

    /**
     * Same as {@link this#meetingStatusText}. Starts with capital symbol and ends with dot.
     */
    @Column(name = "meeting_status_text_2")
    private String meetingStatusText2;
}
