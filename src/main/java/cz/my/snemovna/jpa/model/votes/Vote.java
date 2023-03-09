package cz.my.snemovna.jpa.model.votes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "vote")
@Data
public class Vote implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "organ_id")
    private Long organId;

    @Column(name = "meeting_number")
    private Long meetingNumber;

    @Column(name = "vote_number")
    private Long voteNumber;

    @Column(name = "point_number")
    private Long pointNumber;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "aye")
    private Integer aye;

    @Column(name = "no")
    private Integer no;

    @Column(name = "abstained")
    private Integer abstained;

    @Column(name = "was_not_on_vote")
    private Integer wasNotOnVote;

    @Column(name = "logged_in")
    private Integer loggedIn;

    @Column(name = "quorum")
    private Integer quorum;

    @Column(name = "type", length = 1)
    private String type;

    @Column(name = "result", length = 1)
    private String result;

    @Column(name = "long_name")
    private String longName;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "confused")
    private Boolean isConfused;
}
