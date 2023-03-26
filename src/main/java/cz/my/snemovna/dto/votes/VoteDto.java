package cz.my.snemovna.dto.votes;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The dto class for votes listing.
 */
public record VoteDto(String result, String name, Integer aye,
                      Integer no, Integer abstained, LocalDateTime date) implements Serializable {
}
