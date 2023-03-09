package cz.my.snemovna.service.parsers.votes;

import cz.my.snemovna.jpa.model.votes.Vote;
import cz.my.snemovna.jpa.repository.votes.VoteRepository;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class ConfusingParser extends AbstractSourceParser<Vote, Long> {

    public ConfusingParser(VoteRepository repository) {
        super(repository);
    }

    @Override
    protected Vote convert(List<String> sourceData) {
        final Optional<Vote> voteQuestioning = repository.findById(Long.parseLong(sourceData.get(0)));
        voteQuestioning.ifPresent(e -> e.setIsConfused(true));
        return voteQuestioning.orElse(null);
    }

    @Override
    public String getSourceName() {
        return "zmatecne";
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
