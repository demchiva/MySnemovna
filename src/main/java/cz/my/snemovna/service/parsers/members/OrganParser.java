package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.Organ;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The source parser for organs.
 */
@Component
public class OrganParser extends AbstractSourceParser<Organ> {

    public OrganParser(final JdbcTemplate jdbcTemplate, final EntityManager entityManager) {
        super(Organ.class, jdbcTemplate, entityManager);
    }

    @Override
    protected String getColumnsOrder() {
        return "id,parent_id,organ_type_id,short_name,name_cz,name_en,date_from,date_to,priority,cl_organ_base";
    }

    @Override
    protected Object[] convert(List<String> sourceData) {
        return new Object[] {
                Long.parseLong(sourceData.get(0)),
                safeParseToLong(sourceData.get(1)),
                safeParseToLong(sourceData.get(2)),
                sourceData.get(3),
                sourceData.get(4),
                sourceData.get(5),
                safeParseToLocalDate(sourceData.get(6)),
                safeParseToLocalDate(sourceData.get(7)),
                safeParseToInteger(sourceData.get(8)),
                safeParseToInteger(sourceData.get(9))
        };
    }

    @Override
    public String getSourceName() {
        return "organy";
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.MEMBERS;
    }
}
