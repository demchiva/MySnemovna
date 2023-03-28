package cz.my.snemovna.dto.meetings;

import lombok.Getter;

/**
 * The types of meeting.
 */
public enum MeetingAgendaType {
    PROPOSED(0),
    APPROVED(1);

    @Getter
    private final int type;

    MeetingAgendaType(final int type) {
        this.type = type;
    }
}
