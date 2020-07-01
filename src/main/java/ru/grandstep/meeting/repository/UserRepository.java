package ru.grandstep.meeting.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.grandstep.meeting.model.User;
import ru.grandstep.meeting.model.UserInMeet;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByLogin(String login);
}