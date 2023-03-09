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

@Entity
@Table(name = "member_votes")
@Data
public class MemberVotes implements Serializable {

    @EmbeddedId
    private MemberVotesId memberId;

    @Column(name = "result", length = 1)
    private String result;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class MemberVotesId implements Serializable {
        @Column(name = "memberId")
        private Long memberId;

        @Column(name = "voteId")
        private Long voteId;
    }
}
