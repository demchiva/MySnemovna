package cz.my.snemovna.dto.votes;

import java.io.Serializable;

/**
 * The dto class of members votes in vote detail.
 */
public record VoteMembersDto(String name, String partyName, String photoUrl, String result) implements Serializable {
}
