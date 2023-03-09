package cz.my.snemovna.jpa.model.votes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "vote_questioning")
@Data
public class VoteQuestioning implements Serializable {

    @Id
    @Column(name = "voteId")
    private Long voteId;

    @Column(name = "short_hand_number")
    private Long shortHandNumber;

    @Column(name = "mode")
    private Integer mode;

    @Column(name = "request_vote_id")
    private Long repeatVoteRequestId;

    @Column(name = "repeat_vote_id")
    private Long repeatVoteId;

    @Column(name = "userId")
    private Long userId;
}
