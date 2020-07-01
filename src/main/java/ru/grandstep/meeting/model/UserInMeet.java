package ru.grandstep.meeting.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInMeet {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull//TODO
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private Meet meet;
    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private User user;

    public enum UserRole {
        CREATOR,
        GUEST
    }

    public UserInMeet(Meet meet){
        this.meet = meet;
    }

    public UserInMeet(Meet meet, User user, UserRole role){
        this.meet = meet;
        this.user = user;
        this.role = role;
    }
}
