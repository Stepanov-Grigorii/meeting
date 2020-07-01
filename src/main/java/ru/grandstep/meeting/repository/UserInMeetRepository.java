package ru.grandstep.meeting.repository;

import org.springframework.data.repository.CrudRepository;
import ru.grandstep.meeting.model.Meet;
import ru.grandstep.meeting.model.User;
import ru.grandstep.meeting.model.UserInMeet;

public interface UserInMeetRepository extends CrudRepository<UserInMeet, Integer> {
    UserInMeet findByMeetAndUser(Meet meet, User user);
}
