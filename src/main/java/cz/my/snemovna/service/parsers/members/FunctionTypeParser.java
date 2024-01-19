package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.FunctionType;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The source parser for function types.
 */
@Component
public class FunctionTypeParser extends AbstractSourceParser<FunctionType> {

    public FunctionTypeParser(final JdbcTemplate jdbcTemplate, final EntityManager entityManager) {
        super(FunctionType.class, jdbcTemplate, entityManager);
    }

    @Override
    protected String getColumnsOrder() {
        return "id,organ_type_id,name_cz,name_en,priority,common_type";
    }

    @Override
    protected FunctionType convert(List<String> data) {
        final FunctionType functionType = new FunctionType();

        functionType.setId(Long.parseLong(data.get(0)));
        functionType.setOrganTypeId(safeParseToLong(data.get(1)));
        functionType.setNameCz(data.get(2));
        functionType.setNameEn(data.get(3));
        functionType.setPriority(safeParseToInteger(data.get(4)));
        functionType.setCommonType(safeParseToLong(data.get(5)));

        return functionType;
    }

    @Override
    public String getSourceName() {
        return "typ_funkce";
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.MEMBERS;
    }
}
