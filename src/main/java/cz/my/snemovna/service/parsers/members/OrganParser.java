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
    protected Organ convert(List<String> sourceData) {
        final Organ organ = new Organ();

        organ.setId(Long.parseLong(sourceData.get(0)));
        organ.setParentId(safeParseToLong(sourceData.get(1)));
        organ.setOrganTypeId(safeParseToLong(sourceData.get(2)));
        organ.setShortName(sourceData.get(3));
        organ.setNameCz(sourceData.get(4));
        organ.setNameEn(sourceData.get(5));
        organ.setDateFrom(safeParseToLocalDate(sourceData.get(6)));
        organ.setDateTo(safeParseToLocalDate(sourceData.get(7)));
        organ.setPriority(safeParseToInteger(sourceData.get(8)));
        organ.setClOrganBase(safeParseToInteger(sourceData.get(9)));

        return organ;
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
