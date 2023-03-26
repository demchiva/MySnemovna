package cz.my.snemovna.rest;

import cz.my.snemovna.jpa.model.members.Person;
import cz.my.snemovna.jpa.repository.members.PersonRepository;
import cz.my.snemovna.service.loader.AgendaSourceLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
public class RestMaintenance {

    private final AgendaSourceLoader agendaSourceLoader;
    private final PersonRepository functionRepository;

    @PostMapping("/load")
    public void load() {
        agendaSourceLoader.load();
    }

    @GetMapping("/data")
    public List<Person> getData() {
        return functionRepository.findAll();
    }
}
