package cz.my.snemovna.service.parsers.votes;

import cz.my.snemovna.jpa.model.votes.VoteQuestioning;
import cz.my.snemovna.jpa.repository.votes.VoteQuestioningRepository;
import cz.my.snemovna.service.parsers.AgendaSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoteQuestioningParser extends AbstractVoteWithYearSourceParser<VoteQuestioning, Long> {

    public VoteQuestioningParser(VoteQuestioningRepository repository) {
        super(repository);
    }

    @Override
    protected VoteQuestioning convert(List<String> sourceData) {
        final VoteQuestioning voteQuestioning = new VoteQuestioning();
        voteQuestioning.setVoteId(Long.parseLong(sourceData.get(0)));
        voteQuestioning.setShortHandNumber(safeParseToLong(sourceData.get(1)));
        voteQuestioning.setMode(safeParseToInteger(sourceData.get(2)));
        voteQuestioning.setRepeatVoteRequestId(safeParseToLong(sourceData.get(3)));
        voteQuestioning.setRepeatVoteId(safeParseToLong(sourceData.get(4)));
        return voteQuestioning;
    }

    @Override
    public String getSourceName() {
        return "hlXXXXz";
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.VOTES;
    }
}
