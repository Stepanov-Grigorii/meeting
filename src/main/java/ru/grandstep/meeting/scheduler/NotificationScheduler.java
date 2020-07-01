package ru.grandstep.meeting.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.grandstep.meeting.email.NotificationMessage;
import ru.grandstep.meeting.model.Meet;
import ru.grandstep.meeting.model.UserInMeet;
import ru.grandstep.meeting.repository.MeetRepository;

import javax.mail.MessagingException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    final MeetRepository meetRepository;
    final NotificationMessage notificationMessage;

    @Scheduled(fixedDelay = 60000L)
    void scheduledNotificationSending() throws MessagingException {
        Iterable<Meet> meets = meetRepository.findAll();
        for (Meet meet : meets) {
            if (!meet.isNotificationSent() && meet.getStartDateTime().minusMinutes(15).isBefore(LocalDateTime.now())){
                meet.setNotificationSent(true);
                for (UserInMeet userInMeet : meet.getUsersInMeet()) {
                    notificationMessage.sendNotificationMessage(userInMeet.getUser().getEmail(), "Meeting notification",
                            meet.getName() + " will begin in 15 minutes.");
                }
            }
        }
    }
}
