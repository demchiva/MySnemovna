package cz.my.snemovna.dto.members;

import java.time.LocalDate;

/**
 * The dto class for members listing.
 */
public record MemberDto(Long memberId, String name, String party, String region, LocalDate dateFrom, LocalDate dateTo) {
}
