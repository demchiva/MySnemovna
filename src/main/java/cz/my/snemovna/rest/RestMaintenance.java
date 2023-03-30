package cz.my.snemovna.rest;

import cz.my.snemovna.service.loader.AgendaSourceLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
public class RestMaintenance {

    private final AgendaSourceLoader agendaSourceLoader;

    @PostMapping("/load")
    public void load() {
        agendaSourceLoader.load();
    }
}
