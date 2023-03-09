package cz.my.snemovna.service.parsers.votes;

import cz.my.snemovna.jpa.model.votes.Vote;
import cz.my.snemovna.jpa.repository.votes.VoteRepository;
import cz.my.snemovna.service.parsers.AgendaSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoteParser extends AbstractVoteWithYearSourceParser<Vote, Long> {

    public VoteParser(VoteRepository repository) {
        super(repository);
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
        vote.setIsConfused(false);
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
