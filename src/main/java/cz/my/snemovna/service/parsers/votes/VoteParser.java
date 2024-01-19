package cz.my.snemovna.service.parsers.votes;

import cz.my.snemovna.jpa.model.votes.Vote;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The source parser for votes.
 */
@Component
public class VoteParser extends AbstractVoteWithYearSourceParser<Vote> {

    public VoteParser(final JdbcTemplate jdbcTemplate, final EntityManager entityManager) {
        super(Vote.class, jdbcTemplate, entityManager);
    }

    @Override
    protected String getColumnsOrder() {
        return "id,organ_id,meeting_number,vote_number,point_number,date,time," +
                "aye,no,abstained,was_not_on_vote,logged_in,quorum,type,result,long_name,short_name";
    }

    @Override
    protected Vote convert(List<String> sourceData) {
        final Vote vote = new Vote();

        vote.setId(Long.parseLong(sourceData.get(0)));
        vote.setOrganId(safeParseToLong(sourceData.get(1)));
        vote.setMeetingNumber(safeParseToLong(sourceData.get(2)));
        vote.setVoteNumber(safeParseToLong(sourceData.get(3)));
        vote.setPointNumber(safeParseToLong(sourceData.get(4)));
        vote.setDate(safeParseToLocalDate(sourceData.get(5)));
        vote.setTime(safeParseToLocalTime(sourceData.get(6)));
        vote.setAye(safeParseToInteger(sourceData.get(7)));
        vote.setNo(safeParseToInteger(sourceData.get(8)));
        vote.setAbstained(safeParseToInteger(sourceData.get(9)));
        vote.setWasNotOnVote(safeParseToInteger(sourceData.get(10)));
        vote.setLoggedIn(safeParseToInteger(sourceData.get(11)));
        vote.setQuorum(safeParseToInteger(sourceData.get(12)));
        vote.setType(sourceData.get(13));
        vote.setResult(sourceData.get(14));
        vote.setLongName(sourceData.get(15));
        vote.setShortName(sourceData.get(16));

        return vote;
    }

    @Override
    public String getSourceName() {
        return "hlXXXXs";
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.VOTES;
    }
}
