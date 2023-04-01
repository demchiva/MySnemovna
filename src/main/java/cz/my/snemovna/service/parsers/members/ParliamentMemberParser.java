package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.ParliamentMember;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The source parser for members.
 */
@Component
public class ParliamentMemberParser extends AbstractSourceParser<ParliamentMember> {

    public ParliamentMemberParser(final JdbcTemplate jdbcTemplate, final EntityManager entityManager) {
        super(ParliamentMember.class, jdbcTemplate, entityManager);
    }

    @Override
    protected String getColumnsOrder() {
        return "id,person_id,region_id,party_id,period_id,web,street,municipality,zip," +
                "email,phone,fax,psp_phone,facebook,photo";
    }

    @Override
    protected Object[] convert(List<String> sourceData) {
        return new Object[] {
                Long.parseLong(sourceData.get(0)),
                safeParseToLong(sourceData.get(1)),
                safeParseToLong(sourceData.get(2)),
                safeParseToLong(sourceData.get(3)),
                safeParseToLong(sourceData.get(4)),
                sourceData.get(5),
                sourceData.get(6),
                sourceData.get(7),
                sourceData.get(8),
                sourceData.get(9),
                sourceData.get(10),
                sourceData.get(11),
                sourceData.get(12),
                sourceData.get(13),
                safeParseToBool(sourceData.get(14))
        };
    }

    @Override
    public String getSourceName() {
        return "poslanec";
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.MEMBERS;
    }
}
