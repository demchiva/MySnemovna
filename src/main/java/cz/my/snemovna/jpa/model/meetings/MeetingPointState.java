package cz.my.snemovna.jpa.model.meetings;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

/**
 * The entity class represents all possible meeting point states.
 * Meeting point can be only in one state at the same time.
 */
@Entity
@Table(name = "meeting_point_state")
@Data
public class MeetingPointState implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;
}
