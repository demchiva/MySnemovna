package cz.my.snemovna.service.parsers.members;

import cz.my.snemovna.jpa.model.members.Function;
import cz.my.snemovna.jpa.repository.members.FunctionRepository;
import cz.my.snemovna.service.parsers.AbstractSourceParser;
import cz.my.snemovna.service.parsers.AgendaSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FunctionParser extends AbstractSourceParser<Function, Long> {

    public FunctionParser(FunctionRepository repository) {
        super(repository);
    }

    @Override
    protected Function convert(List<String> sourceData) {
        final Function function = new Function();
        function.setId(Long.parseLong(sourceData.get(0)));
        function.setOrganId(safeParseToLong(sourceData.get(1)));
        function.setFunctionTypeId(safeParseToLong(sourceData.get(2)));
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
