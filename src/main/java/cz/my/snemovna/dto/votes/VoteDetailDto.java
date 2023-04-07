package cz.my.snemovna.dto.votes;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The dto class of vote detail.
 */
public record VoteDetailDto(Long voteId, String result, Integer aye,
                            Integer no, Integer abstained, LocalDateTime date,
                            Long voteNumber, Long pointNumber, Integer quorum,
                            String psUrl, String longName, String pointName,
                            String pointState, String pointType, Long meetingNumber) implements Serializable {
}
