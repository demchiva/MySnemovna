package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.Function;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The source parser for functions.
 */
@Component
public class FunctionParser extends AbstractSourceParser<Function> {

    public FunctionParser(final JdbcTemplate jdbcTemplate, final EntityManager entityManager) {
        super(Function.class, jdbcTemplate, entityManager);
    }

    @Override
    protected String getColumnsOrder() {
        return "id,organ_id,function_type_id,name_cz,priority";
    }

    @Override
    protected Object[] convert(List<String> sourceData) {
        return new Object[] {
                Long.parseLong(sourceData.get(0)),
                safeParseToLong(sourceData.get(1)),
                safeParseToLong(sourceData.get(2)),
                sourceData.get(3),
                safeParseToInteger(sourceData.get(4))
        };
    }

    @Override
    public String getSourceName() {
        return "funkce";
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.MEMBERS;
    }
}
