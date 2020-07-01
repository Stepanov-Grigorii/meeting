package ru.grandstep.meeting.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.grandstep.meeting.constraint.annotation.DateConstraint;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Meet {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private boolean notificationSent;
    @DateConstraint
    private LocalDateTime startDateTime;
    @OneToMany(mappedBy = "meet", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<UserInMeet> usersInMeet;
    private Integer durationMinutes;

    public Meet(String name, LocalDateTime startDateTime, Integer durationMinutes){
        this.name = name;
        this.startDateTime = startDateTime;
        this.durationMinutes = durationMinutes;
    }
}
