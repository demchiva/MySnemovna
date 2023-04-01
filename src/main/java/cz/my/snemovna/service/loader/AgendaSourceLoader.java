package cz.my.snemovna.service.loader;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendaSourceLoader implements IAgendaSourceLoader {

    private final List<SourceLoader> sourceLoaders;

    private void loadAgendaSourceData() {
        sourceLoaders.forEach(SourceLoader::load);
    }

    private void fillDatabaseWithAgendaSourceData() {
        sourceLoaders.stream().sorted(Comparator.comparing(SourceLoader::getPriority)).forEach(SourceLoader::fill);
    }

    private void deleteLoadedData() {
        sourceLoaders.forEach(SourceLoader::delete);
    }

    @Override
    public void load() {
        try {
            loadAgendaSourceData();
            fillDatabaseWithAgendaSourceData();
        } finally {
            deleteLoadedData();
        }
    }
}
