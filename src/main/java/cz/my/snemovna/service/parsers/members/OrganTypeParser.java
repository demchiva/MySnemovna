package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.OrganType;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The source parser for organ types.
 */
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
    protected OrganType convert(List<String> data) {
        final OrganType organType = new OrganType();

        organType.setId(Long.parseLong(data.get(0)));
        organType.setParentId(safeParseToLong(data.get(1)));
        organType.setNameCz(data.get(2));
        organType.setNameEn(data.get(3));
        organType.setCommonType(safeParseToLong(data.get(4)));
        organType.setPriority(safeParseToInteger(data.get(5)));

        return organType;
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
