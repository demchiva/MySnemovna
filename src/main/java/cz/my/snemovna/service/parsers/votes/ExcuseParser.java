package cz.my.snemovna.service.parsers.votes;

import cz.my.snemovna.jpa.model.votes.Excuse;
import cz.my.snemovna.jpa.repository.votes.ExcuseRepository;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExcuseParser extends AbstractSourceParser<Excuse, Long> {

    public ExcuseParser(ExcuseRepository repository) {
        super(repository);
    }

    @Override
    protected Excuse convert(List<String> sourceData) {
        final Excuse excuse = new Excuse();
        excuse.setOrganId(safeParseToLong(sourceData.get(0)));
        excuse.setMemberId(safeParseToLong(sourceData.get(1)));
        excuse.setDate(safeParseToLocalDate(sourceData.get(2)));
        excuse.setTimeFrom(safeParseToLocalTime(sourceData.get(3)));
        excuse.setTimeTo(safeParseToLocalTime(sourceData.get(4)));
        return excuse;
    }

    @Override
    public String getSourceName() {
        return "omluvy";
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.VOTES;
    }
}
