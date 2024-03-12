package cz.my.snemovna.rest;

import cz.my.snemovna.service.loader.IAgendaSourceLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The maintenance for tests. NOT PUBLIC !!!
 */
@RestController
@RequestMapping("/api/maintenance")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class RestMaintenance {

    private final IAgendaSourceLoader agendaSourceLoader;

    /**
     * The method reloads database data.
     */
    @PostMapping("/load")
    public void load() {
        agendaSourceLoader.load();
    }
}
