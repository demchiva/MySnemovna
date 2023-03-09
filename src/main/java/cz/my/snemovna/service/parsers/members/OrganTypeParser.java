package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.OrganType;
import cz.my.snemovna.jpa.repository.members.OrganTypeRepository;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrganTypeParser extends AbstractSourceParser<OrganType, Long> {

    public OrganTypeParser(OrganTypeRepository repository) {
        super(repository);
    }

    @Override
    protected OrganType convert(List<String> data) {
        OrganType organType = new OrganType();
        organType.setId(Long.parseLong(data.get(0)));
        organType.setParentId(safeParseToLong(data.get(1)));
        organType.setNameCz(data.get(2));
        organType.setNameEn(data.get(3));
        organType.setCommonType(safeParseToLong(data.get(4)));
        organType.setPriority(safeParseToInteger(data.get(5)));
        return organType;
    }

    @Override
    public String getSourceName() {
        return "typ_organu";
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.MEMBERS;
    }
}
