package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.FunctionType;
import cz.my.snemovna.jpa.repository.members.FunctionTypeRepository;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FunctionTypeParser extends AbstractSourceParser<FunctionType, Long> {

    public FunctionTypeParser(FunctionTypeRepository repository) {
        super(repository);
    }

    @Override
    protected FunctionType convert(List<String> data) {
        final FunctionType functionType = new FunctionType();
        functionType.setId(Long.parseLong(data.get(0)));
        functionType.setOrganTypeId(safeParseToLong(data.get(1)));
        functionType.setNameCz(data.get(2));
        functionType.setNameEn(data.get(3));
        functionType.setPriority(safeParseToInteger(data.get(4)));
        functionType.setCommonType(safeParseToLong(data.get(5)));
        return functionType;
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
