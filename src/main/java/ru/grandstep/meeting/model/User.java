package ru.grandstep.meeting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank(message = "field login can't be blank")
    private String login;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", message = "Incorrect password")
    private String password;
    @Pattern(regexp = "^(.+)@(.+)$", message = "Incorrect email")
    private String email;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<UserInMeet> usersInMeet;

    public User(String login){
        this.login = login;
    }

    public User(String login, String password, String email){
        this.login = login;
        this.password = password;
        this.email = email;
    }
}