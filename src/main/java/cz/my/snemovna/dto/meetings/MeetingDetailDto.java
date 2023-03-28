package cz.my.snemovna.dto.meetings;

import java.util.List;

/**
 * The dto class of meeting detail.
 */
public record MeetingDetailDto(List<MeetingPointDto> meetingPoints) {

    public record MeetingPointDto(String name, String state, String type) {

    }
}
