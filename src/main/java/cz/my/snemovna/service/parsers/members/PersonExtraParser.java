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
    protected Object[] convert(List<String> sourceData) {
        return new Object[] {
                Long.parseLong(sourceData.get(0)),
                safeParseToLong(sourceData.get(1)),
                safeParseToInteger(sourceData.get(2)),
                safeParseToLong(sourceData.get(3)),
                sourceData.get(4),
                safeParseToLong(sourceData.get(5))
        };
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
