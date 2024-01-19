package cz.my.snemovna.rest;

import cz.my.snemovna.service.loader.IAgendaSourceLoader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The maintenance for tests. NOT PUBLIC !!!
 */
@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
@Slf4j
public class RestMaintenance {

    private final IAgendaSourceLoader agendaSourceLoader;

    /**
     * The method reloads database data.
     */
    @PostMapping("/load")
    @PostConstruct
    public void load() {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        agendaSourceLoader.load();
        stopWatch.stop();
        LOGGER.info("Finished loading data. Loading took {} ms.", stopWatch.getTotalTimeMillis());
    }
}
