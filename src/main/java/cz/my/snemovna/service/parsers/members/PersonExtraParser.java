package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.PersonExtra;
import cz.my.snemovna.jpa.repository.members.PersonExtraRepository;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonExtraParser extends AbstractSourceParser<PersonExtra, Long> {

    public PersonExtraParser(PersonExtraRepository repository) {
        super(repository);
    }

    @Override
    protected PersonExtra convert(List<String> sourceData) {
        final PersonExtra personExtra = new PersonExtra();
        personExtra.setPersonId(Long.parseLong(sourceData.get(0)));
        personExtra.setOrganId(safeParseToLong(sourceData.get(1)));
        personExtra.setType(safeParseToInteger(sourceData.get(2)));
        personExtra.setDistrict(safeParseToLong(sourceData.get(3)));
        personExtra.setParty(sourceData.get(4));
        personExtra.setExternalId(safeParseToLong(sourceData.get(5)));
        return personExtra;
    }

    @Override
    public String getSourceName() {
        return "osoba_extra";
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.MEMBERS;
    }
}
