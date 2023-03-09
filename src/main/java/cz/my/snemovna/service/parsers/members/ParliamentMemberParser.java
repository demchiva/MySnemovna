package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.ParliamentMember;
import cz.my.snemovna.jpa.repository.members.ParliamentMemberRepository;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParliamentMemberParser extends AbstractSourceParser<ParliamentMember, Long> {

    public ParliamentMemberParser(ParliamentMemberRepository repository) {
        super(repository);
    }

    @Override
    protected ParliamentMember convert(List<String> sourceData) {
        final ParliamentMember member = new ParliamentMember();
        member.setId(Long.parseLong(sourceData.get(0)));
        member.setPersonId(safeParseToLong(sourceData.get(1)));
        member.setRegionId(safeParseToLong(sourceData.get(2)));
        member.setPartyId(safeParseToLong(sourceData.get(3)));
        member.setPeriodId(safeParseToLong(sourceData.get(4)));
        member.setWeb(sourceData.get(5));
        member.setStreet(sourceData.get(6));
        member.setMunicipality(sourceData.get(7));
        member.setZip(sourceData.get(8));
        member.setEmail(sourceData.get(9));
        member.setPhone(sourceData.get(10));
        member.setFax(sourceData.get(11));
        member.setPspPhone(sourceData.get(12));
        member.setFacebook(sourceData.get(13));
        member.setPhoto(safeParseToBool(sourceData.get(14)));
        return member;
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
