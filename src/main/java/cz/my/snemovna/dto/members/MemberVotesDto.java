package cz.my.snemovna.dto.members;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The dto class of members votes in member detail.
 */
public record MemberVotesDto(String name, LocalDateTime date, String result, Long voteId) implements Serializable {
}
