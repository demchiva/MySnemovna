package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.Person;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonParser extends AbstractSourceParser<Person> {

    public PersonParser(final JdbcTemplate jdbcTemplate, final EntityManager entityManager) {
        super(Person.class, jdbcTemplate, entityManager);
    }

    @Override
    protected String getColumnsOrder() {
        return "id,title_before,first_name,last_name,title_after,birthdate,sex,updated_at,date_of_death";
    }

    @Override
    protected Object[] convert(List<String> sourceData) {
        return new Object[] {
                Long.parseLong(sourceData.get(0)),
                sourceData.get(1),
                sourceData.get(2),
                sourceData.get(3),
                sourceData.get(4),
                safeParseToLocalDate(sourceData.get(5)),
                sourceData.get(6),
                safeParseToLocalDate(sourceData.get(7)),
                safeParseToLocalDate(sourceData.get(8))
        };
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
