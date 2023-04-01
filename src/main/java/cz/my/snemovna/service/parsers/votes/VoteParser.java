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
    protected Object[] convert(List<String> sourceData) {
        return new Object[] {
                Long.parseLong(sourceData.get(0)),
                safeParseToLong(sourceData.get(1)),
                safeParseToLong(sourceData.get(2)),
                safeParseToLong(sourceData.get(3)),
                safeParseToLong(sourceData.get(4)),
                safeParseToLocalDate(sourceData.get(5)),
                safeParseToLocalTime(sourceData.get(6)),
                safeParseToInteger(sourceData.get(7)),
                safeParseToInteger(sourceData.get(8)),
                safeParseToInteger(sourceData.get(9)),
                safeParseToInteger(sourceData.get(10)),
                safeParseToInteger(sourceData.get(11)),
                safeParseToInteger(sourceData.get(12)),
                sourceData.get(13),
                sourceData.get(14),
                sourceData.get(15),
                sourceData.get(16)
        };
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
