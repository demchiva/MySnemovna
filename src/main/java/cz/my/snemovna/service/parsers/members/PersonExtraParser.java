package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.PersonExtra;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The source parser for additional person information.
 */
@Component
public class PersonExtraParser extends AbstractSourceParser<PersonExtra> {

    public PersonExtraParser(final JdbcTemplate jdbcTemplate, final EntityManager entityManager) {
        super(PersonExtra.class, jdbcTemplate, entityManager);
    }

    @Override
    protected String getColumnsOrder() {
        return "person_id,organ_id,type,district,party,external_id";
    }

    @Override
    protected PersonExtra convert(List<String> sourceData) {
        final PersonExtra personExtra = new PersonExtra();

        personExtra.setId(Long.parseLong(sourceData.get(0)));
        personExtra.setPersonId(safeParseToLong(sourceData.get(1)));
        // personExtra.setOrganId(????);
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
