package ru.grandstep.meeting.repository;

import org.springframework.data.repository.CrudRepository;
import ru.grandstep.meeting.model.Meet;


public interface MeetRepository extends CrudRepository<Meet, Integer> {
    Meet findByName(String name);
    void deleteByName(String name);
    boolean existsByName(String name);
}