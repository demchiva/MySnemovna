package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.RegionalOffice;
import cz.my.snemovna.jpa.repository.members.RegionalOfficeRepository;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegionalOfficeParser extends AbstractSourceParser<RegionalOffice, Long> {

    public RegionalOfficeParser(RegionalOfficeRepository repository) {
        super(repository);
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
