package cz.my.snemovna.dto.meetings;

import java.time.LocalDateTime;

/**
 * The dto class for meeting listing.
 */
public record MeetingDto(Long meetingId, Long meetingNumber, String state,
                         String date, String type, String organName,
                         LocalDateTime dateFrom, LocalDateTime dateTo) {
}
