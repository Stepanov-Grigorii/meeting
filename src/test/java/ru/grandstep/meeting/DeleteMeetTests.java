package ru.grandstep.meeting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.grandstep.meeting.model.Meet;
import ru.grandstep.meeting.repository.MeetRepository;
import ru.grandstep.meeting.service.MeetService;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
class DeleteMeetTests {

    @Autowired
    public MeetRepository meetRepository;

    @Autowired
    public MeetService meetService;

    Meet meet;

    @BeforeEach
    void setUp() {
        meetRepository.deleteAll();
        meet = meetRepository.save(new Meet("Meet1", LocalDateTime.now().plusDays(3), 60));
    }

    @Test
    void deleteMeetSuccessfully() {
        meetService.delete(meet.getName());
        Assertions.assertNull(meetRepository.findByName(meet.getName()));
    }
    @Test
    void deleteMeetNoneExistenceName(){
        Assertions.assertThrows(RuntimeException.class, () -> meetService.delete("NoneExistenceName"), "meet with name NoneExistenceName not found");
    }
}
