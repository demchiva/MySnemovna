package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.Person;
import cz.my.snemovna.jpa.repository.members.PersonRepository;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonParser extends AbstractSourceParser<Person, Long> {

    public PersonParser(PersonRepository repository) {
        super(repository);
    }

    @Override
    protected Person convert(List<String> sourceData) {
        final Person person = new Person();
        person.setId(Long.parseLong(sourceData.get(0)));
        person.setTitleBeforeName(sourceData.get(1));
        person.setFirstName(sourceData.get(2));
        person.setLastName(sourceData.get(3));
        person.setTitleAfterName(sourceData.get(4));
        person.setBirthdate(safeParseToLocalDate(sourceData.get(5)));
        person.setSex(sourceData.get(6));
        person.setUpdatedAt(safeParseToLocalDate(sourceData.get(7)));
        person.setDateOfDeath(safeParseToLocalDate(sourceData.get(8)));
        return person;
    }

    @Override
    public String getSourceName() {
        return "osoby";
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.MEMBERS;
    }
}
