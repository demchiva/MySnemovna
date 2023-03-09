package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.Enrollment;
import cz.my.snemovna.jpa.repository.members.EnrollmentRepository;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnrollmentParser extends AbstractSourceParser<Enrollment, Long> {

    public EnrollmentParser(EnrollmentRepository repository) {
        super(repository);
    }

    @Override
    protected Enrollment convert(List<String> sourceData) {
        final Enrollment enrollment = new Enrollment();
        enrollment.setId(Long.parseLong(sourceData.get(0)));
        enrollment.setMembershipOrFunctionId(safeParseToLong(sourceData.get(1)));
        enrollment.setMembershipOrFunction(safeParseToInteger(sourceData.get(2)));
        enrollment.setEnrollmentFrom(safeParseDateWithHours(sourceData.get(3)));
        enrollment.setEnrollmentTo(safeParseDateWithHours(sourceData.get(4)));
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
