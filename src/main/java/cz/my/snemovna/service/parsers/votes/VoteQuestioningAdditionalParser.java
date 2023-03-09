package cz.my.snemovna.service.parsers.votes;

import cz.my.snemovna.jpa.model.votes.VoteQuestioning;
import cz.my.snemovna.service.parsers.AgendaSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class VoteQuestioningAdditionalParser extends AbstractVoteWithYearSourceParser<VoteQuestioning, Long> {

    public VoteQuestioningAdditionalParser(JpaRepository<VoteQuestioning, Long> repository) {
        super(repository);
    }

    @Override
    protected VoteQuestioning convert(List<String> sourceData) {
        final Optional<VoteQuestioning> voteQuestioning = repository.findById(Long.parseLong(sourceData.get(0)));
        voteQuestioning.ifPresent(e -> e.setUserId(safeParseToLong(sourceData.get(1))));
        return voteQuestioning.orElse(null);
    }

    @Override
    public String getSourceName() {
        return "hlXXXXx";
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.VOTES;
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
