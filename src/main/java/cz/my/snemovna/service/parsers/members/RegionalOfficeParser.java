package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.RegionalOffice;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegionalOfficeParser extends AbstractSourceParser<RegionalOffice> {

    public RegionalOfficeParser(final JdbcTemplate jdbcTemplate, final EntityManager entityManager) {
        super(RegionalOffice.class, jdbcTemplate, entityManager);
    }

    @Override
    protected String getColumnsOrder() {
        return "member_id,address,latitude,longitude";
    }

    @Override
    protected Object[] convert(List<String> sourceData) {
        return new Object[] {
                Long.parseLong(sourceData.get(0)),
                sourceData.get(1),
                sourceData.get(2),
                sourceData.get(3)
        };
    }

    @Override
    public String getSourceName() {
        return "pkgps";
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.MEMBERS;
    }
}
