package ru.grandstep.meeting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.grandstep.meeting.model.Meet;
import ru.grandstep.meeting.model.User;
import ru.grandstep.meeting.model.UserInMeet;
import ru.grandstep.meeting.repository.MeetRepository;
import ru.grandstep.meeting.repository.UserInMeetRepository;
import ru.grandstep.meeting.repository.UserRepository;
import ru.grandstep.meeting.service.MeetService;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteUserTests {

    @Autowired
    public MeetRepository meetRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public UserInMeetRepository userInMeetRepository;

    @Autowired
    public MeetService meetService;

    Meet meet;
    User user;

    @BeforeEach
    void setUp() {
        meetRepository.deleteAll();
        userRepository.deleteAll();

        user = userRepository.save(new User("Login1"));
        meet = meetRepository.save(new Meet("Meet1", LocalDateTime.now().plusSeconds(3), 60));
        userInMeetRepository.save(new UserInMeet(meet, user, UserInMeet.UserRole.CREATOR));
    }

    @Test
    void deleteUserSuccessfully(){
        meetService.deleteUser(meet.getName(), user.getLogin());
        Assertions.assertNull(userInMeetRepository.findByMeetAndUser(meet, user));
    }

}
