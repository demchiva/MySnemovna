package cz.my.snemovna.config;

import cz.my.snemovna.service.loader.IAgendaSourceLoader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * The source configuration. Loads all data to db in cron job.
 */
@EnableScheduling
@Configuration
@Profile("!test")
@RequiredArgsConstructor
public class SourcesConfiguration {

    private final IAgendaSourceLoader agendaSourceLoader;

    /**
     * Regularly fills the database with new data.
     * Starts every day at 03:05 AM.
     */
    @PostConstruct
    @Scheduled(cron = "0 5 3 * * ?")
    public void loadDatabase() {
        agendaSourceLoader.load();
    }
}
