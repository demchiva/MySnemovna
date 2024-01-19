package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.Enrollment;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The source parser for member enrollments to organs or functions.
 */
@Component
public class EnrollmentParser extends AbstractSourceParser<Enrollment> {

    public EnrollmentParser(final JdbcTemplate jdbcTemplate, final EntityManager entityManager) {
        super(Enrollment.class, jdbcTemplate, entityManager);
    }

    @Override
    protected String getColumnsOrder() {
        return "person_id,id_of,type,enrollment_from,enrollment_to,mandate_from,mandate_to";
    }

    @Override
    protected Enrollment convert(List<String> sourceData) {
        Enrollment enrollment = new Enrollment();

        enrollment.setId(Long.parseLong(sourceData.get(0)));
        enrollment.setMembershipOrFunctionId(safeParseToLong(sourceData.get(1)));
        enrollment.setMembershipOrFunction(safeParseToInteger(sourceData.get(2)));
        enrollment.setEnrollmentFrom(sourceData.get(3));
        enrollment.setEnrollmentTo(sourceData.get(4));
        enrollment.setMandateFrom(safeParseToLocalDate(sourceData.get(5)));
        enrollment.setMandateTo(safeParseToLocalDate(sourceData.get(6)));

        return enrollment;
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
