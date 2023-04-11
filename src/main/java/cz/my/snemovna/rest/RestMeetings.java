package cz.my.snemovna.rest;

import cz.my.snemovna.dto.meetings.MeetingAgendaType;
import cz.my.snemovna.dto.meetings.MeetingDetailDto;
import cz.my.snemovna.dto.meetings.MeetingDto;
import cz.my.snemovna.rest.api.IRestMeetings;
import cz.my.snemovna.service.IMeetingsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The api for manage meetings agenda.
 */
@RequestMapping("/api/v1/meetings")
@RestController
@RequiredArgsConstructor
public class RestMeetings implements IRestMeetings {

    private final IMeetingsService meetingsService;

    /**
     * Get the meetings for meetings listing.
     * @param page the page.
     * @return the meetings page.
     */
    @GetMapping("")
    @Override
    public Page<MeetingDto> getMeetings(@Valid final PageApiRequest page) {
        return meetingsService.getMeetings(PageRequest.of(page.getPage(), page.getSize(),
                page.getDirection(), page.getProperty().split(",")));
    }

    /**
     * Get meeting detail.
     * @param meetingId meeting id.
     * @return the meeting detail.
     */
    @GetMapping("/{meetingId}")
    @Override
    public MeetingDetailDto getMeeting(@PathVariable("meetingId") @NotNull final Long meetingId,
                                       @RequestParam("type") @NotNull final MeetingAgendaType type) {
        return meetingsService.getMeeting(meetingId, type);
    }
}
