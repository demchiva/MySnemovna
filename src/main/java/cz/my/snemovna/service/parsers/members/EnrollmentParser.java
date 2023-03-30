package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.Enrollment;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

//@Component
public class EnrollmentParser extends AbstractSourceParser<Enrollment> {

    public EnrollmentParser(final JdbcTemplate jdbcTemplate, final EntityManager entityManager) {
        super(Enrollment.class, jdbcTemplate, entityManager);
    }

    @Override
    protected String getColumnsOrder() {
        return "person_id,id_of,type,enrollment_from,enrollment_to,mandate_from,mandate_to";
    }

    @Override
    protected Object[] convert(List<String> sourceData) {
        return new Object[] {
                Long.parseLong(sourceData.get(0)),
                safeParseToLong(sourceData.get(1)),
                safeParseToInteger(sourceData.get(2)),
                sourceData.get(3),
                sourceData.get(4),
                safeParseToLocalDate(sourceData.get(5)),
                safeParseToLocalDate(sourceData.get(6))
        };
    }

    @Override
    public String getSourceName() {
        return "zarazeni";
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.MEMBERS;
    }
}
