package cz.my.snemovna.jpa.model.votes;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The entity represents member voting result in particular vote.
 * Member has only one result in particular vote.
 */
@Entity
@Table(name = "member_votes")
@Data
public class MemberVotes implements Serializable {

    @EmbeddedId
    private MemberVotesId id;

    @Column(name = "result", length = 1)
    private String result;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class MemberVotesId implements Serializable {
        @Column(name = "member_id")
        private Long memberId;

        @Column(name = "vote_id")
        private Long voteId;
    }
}
