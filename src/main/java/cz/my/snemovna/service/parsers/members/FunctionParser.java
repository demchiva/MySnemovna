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
    protected Function convert(List<String> sourceData) {
        final Function function = new Function();

        function.setId(Long.parseLong(sourceData.get(0)));
        function.setOrganId(Long.parseLong(sourceData.get(1)));
        function.setFunctionTypeId(Long.parseLong(sourceData.get(2)));
        function.setNameCz(sourceData.get(3));
        function.setPriority(safeParseToInteger(sourceData.get(4)));

        return function;
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
