package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.OrganType;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrganTypeParser extends AbstractSourceParser<OrganType> {

    public OrganTypeParser(final JdbcTemplate jdbcTemplate, final EntityManager entityManager) {
        super(OrganType.class, jdbcTemplate, entityManager);
    }

    @Override
    protected String getColumnsOrder() {
        return "id,parent_id,name_cz,name_en,common_type,priority";
    }

    @Override
    protected Object[] convert(List<String> data) {
        return new Object[] {
                Long.parseLong(data.get(0)),
                safeParseToLong(data.get(1)),
                data.get(2),
                data.get(3),
                safeParseToLong(data.get(4)),
                safeParseToInteger(data.get(5))
        };
    }

    @Override
    public String getSourceName() {
        return "typ_organu";
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.MEMBERS;
    }
}
