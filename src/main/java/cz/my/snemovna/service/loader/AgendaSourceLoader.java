package cz.my.snemovna.service.loader;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendaSourceLoader {

    private final List<SourceLoader> sourceLoaders;

    public void loadAgendaSourceData() {
        sourceLoaders.forEach(SourceLoader::load);
    }

    public void fillDatabaseWithAgendaSourceData() {
        sourceLoaders.stream().sorted(Comparator.comparing(SourceLoader::getPriority)).forEach(SourceLoader::fill);
    }

    public void deleteLoadedData() {
        sourceLoaders.forEach(SourceLoader::delete);
    }

    public void load() {
        try {
            loadAgendaSourceData();
            fillDatabaseWithAgendaSourceData();
        } finally {
            deleteLoadedData();
        }
    }
}
