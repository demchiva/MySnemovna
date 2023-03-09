package cz.my.snemovna.jpa.model.meetings;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MeetingAgendaId implements Serializable {

    @Column(name = "id")
    private Long id;

    @Column(name = "agenda_type")
    private Integer agendaType;
}
