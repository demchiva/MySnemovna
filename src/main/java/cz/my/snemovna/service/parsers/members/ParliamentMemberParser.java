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
    protected ParliamentMember convert(List<String> sourceData) {
        final ParliamentMember parliamentMember = new ParliamentMember();

        parliamentMember.setId(Long.parseLong(sourceData.get(0)));
        parliamentMember.setPersonId(safeParseToLong(sourceData.get(1)));
        parliamentMember.setRegionId(safeParseToLong(sourceData.get(2)));
        parliamentMember.setPartyId(safeParseToLong(sourceData.get(3)));
        parliamentMember.setPeriodId(safeParseToLong(sourceData.get(4)));
        parliamentMember.setWeb(sourceData.get(5));
        parliamentMember.setStreet(sourceData.get(6));
        parliamentMember.setMunicipality(sourceData.get(7));
        parliamentMember.setZip(sourceData.get(8));
        parliamentMember.setEmail(sourceData.get(9));
        parliamentMember.setPhone(sourceData.get(10));
        parliamentMember.setFax(sourceData.get(11));
        parliamentMember.setPspPhone(sourceData.get(12));
        parliamentMember.setFacebook(sourceData.get(13));
        parliamentMember.setPhoto(safeParseToBool(sourceData.get(14)));

        return parliamentMember;
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
