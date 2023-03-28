package cz.my.snemovna.service;

import cz.my.snemovna.dto.meetings.MeetingAgendaType;
import cz.my.snemovna.dto.meetings.MeetingDetailDto;
import cz.my.snemovna.dto.meetings.MeetingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The service for manage meetings agenda.
 */
public interface IMeetingsService {

    /**
     * The method get meetings list.
     * @param page the page
     * @return the meetings page.
     */
    Page<MeetingDto> getMeetings(Pageable page);

    /**
     * The method get meeting detail.
     * @param meetingId meeting id.
     * @param type agenda type.
     * @return the meeting detail.
     */
    MeetingDetailDto getMeeting(Long meetingId, MeetingAgendaType type);
}
