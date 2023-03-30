package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.FunctionType;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

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
    protected Object[] convert(List<String> data) {
        return new Object[] {
                Long.parseLong(data.get(0)),
                safeParseToLong(data.get(1)),
                data.get(2),
                data.get(3),
                safeParseToInteger(data.get(4)),
                safeParseToLong(data.get(5))
        };
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
