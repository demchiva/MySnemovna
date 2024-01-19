package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.RegionalOffice;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The source parser for regional office.
 */
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
    protected RegionalOffice convert(List<String> sourceData) {
        final RegionalOffice regionalOffice = new RegionalOffice();

        regionalOffice.setMemberId(Long.parseLong(sourceData.get(0)));
        regionalOffice.setAddress(sourceData.get(1));
        regionalOffice.setLatitude(sourceData.get(2));
        regionalOffice.setLongitude(sourceData.get(3));

        return regionalOffice;
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
