package cz.my.snemovna.service.parsers.votes;

import cz.my.snemovna.jpa.model.votes.MemberVotes;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberVotesParser2 extends AbstractVoteWithYearSourceParser<MemberVotes> {

    public MemberVotesParser2(final JdbcTemplate jdbcTemplate, final EntityManager entityManager) {
        super(MemberVotes.class, jdbcTemplate, entityManager);
    }

    @Override
    protected String getColumnsOrder() {
        return "member_id,vote_id,result";
    }

    @Override
    protected Object[] convert(List<String> sourceData) {
        return new Object[] {
                Long.parseLong(sourceData.get(0)),
                Long.parseLong(sourceData.get(1)),
                sourceData.get(2)
        };
    }

    @Override
    public String getSourceName() {
        return "hlXXXXh2";
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.VOTES;
    }
}

