package cz.my.snemovna.config;

import cz.my.snemovna.service.loader.AgendaSourceLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
@Profile("!test")
@RequiredArgsConstructor
public class SourcesConfiguration {

    private final AgendaSourceLoader agendaSourceLoader;

    /**
     * Regularly fills the database with new data.
     * Starts every day at 03:05 AM.
     */
    @Scheduled(cron = "0 5 3 * * ?")
    public void loadDatabase() {
        agendaSourceLoader.load();
    }
}
