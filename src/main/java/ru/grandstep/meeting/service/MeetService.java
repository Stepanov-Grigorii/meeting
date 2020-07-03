package ru.grandstep.meeting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.grandstep.meeting.dto.MeetDataDto;
import ru.grandstep.meeting.email.NotificationMessage;
import ru.grandstep.meeting.model.Meet;
import ru.grandstep.meeting.model.User;
import ru.grandstep.meeting.model.UserInMeet;
import ru.grandstep.meeting.repository.MeetRepository;
import ru.grandstep.meeting.repository.UserInMeetRepository;
import ru.grandstep.meeting.repository.UserRepository;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class MeetService {

    private final MeetRepository meetRepository;
    private final UserRepository userRepository;
    private final UserInMeetRepository userInMeetRepository;
    final NotificationMessage notificationMessage;

    public List<MeetDataDto> getAll() {

        List<Meet> meets = (List<Meet>) meetRepository.findAll();
        List<MeetDataDto> meetDataList = new ArrayList<>();

        for (Meet meet : meets) {
            MeetDataDto meetDataDto = new MeetDataDto();
            meetDataDto.setName(meet.getName());
            meetDataDto.setStartDate(meet.getStartDateTime());
            meetDataDto.setDuration(meet.getDurationMinutes());
            meetDataDto.setLogins(meet.getUsersInMeet().stream()
                    .filter(uim -> uim.getRole().equals(UserInMeet.UserRole.GUEST))
                    .map(UserInMeet::getUser)
                    .map(User::getLogin)
                    .collect(Collectors.toUnmodifiableList()));
            meetDataDto.setCreatorLogin(meet.getUsersInMeet().stream()
                    .filter(uim -> uim.getRole().equals(UserInMeet.UserRole.CREATOR))
                    .findFirst()
                    .map(UserInMeet::getUser)
                    .map(User::getLogin).get());
            meetDataList.add(meetDataDto);
        }

        return meetDataList;
    }

    public String addUser(String meetName, String userLogin) throws MessagingException {
        Meet meet = meetRepository.findByName(meetName);
        User user = userRepository.findUserByLogin(userLogin);

        return addUser(meet, user, UserInMeet.UserRole.GUEST);
    }

    public String addCreator(Meet meet, String creatorLogin) throws MessagingException {
        User user = userRepository.findUserByLogin(creatorLogin);
        return addUser(meet, user, UserInMeet.UserRole.CREATOR);
    }

    public String addUser(Meet meet, User user, UserInMeet.UserRole role) throws MessagingException {

        UserInMeet userInMeet = new UserInMeet(meet, user, role);

        notificationMessage.sendNotificationMessage(user.getEmail(), "Meeting notification",
                "Dear " + user.getLogin() + ", you are invited to " + meet.getName() + " at "
                        + meet.getStartDateTime().toString() + ".");


        String result = crossingCheck(user, meet.getStartDateTime(), meet.getDurationMinutes())
                ? "User: " + user.getLogin() + " intersects meetings"
                : null;

        userInMeetRepository.save(userInMeet);

        return result;
    }

    public List<String> save(Meet meet, List<String> userLogins, String creatorLogin) throws MessagingException {
        List<String> warnings = new ArrayList<>();
        for (String userLogin : userLogins) {
            String warningResult = addUser(meet, userRepository.findUserByLogin(userLogin), UserInMeet.UserRole.GUEST);
            if (warningResult != null) {
                warnings.add(warningResult);
            }
        }
        String warningResult = addCreator(meet, creatorLogin);

        if (warningResult != null) {
            warnings.add(warningResult);
        }

        meetRepository.save(meet);
        return warnings;
    }

    public void deleteUser(String meetName, String userLogin) {
        Meet meet = meetRepository.findByName(meetName);
        List<UserInMeet> userInMeets = meet.getUsersInMeet();
        for (UserInMeet userInMeet : userInMeets) {
            if (userInMeet.getUser().getLogin().equals(userLogin)) {
                userInMeetRepository.delete(userInMeet);
            }
        }
    }

    @Transactional
    public void delete(String name) {
        if (meetRepository.existsByName(name)) {
            meetRepository.deleteByName(name);
        } else {
            throw new RuntimeException("meet with name " + name + " not found");
        }
    }

    public boolean crossingCheck(User user, LocalDateTime meetStartTime, Integer durationMinutes) {
        List<UserInMeet> userInMeets = user.getUsersInMeet();

        for (UserInMeet userInMeet : userInMeets) {
            Integer anotherMeetDuration = userInMeet.getMeet().getDurationMinutes();
            LocalDateTime startDateTime = userInMeet.getMeet().getStartDateTime();
            LocalDateTime endDateTime = userInMeet.getMeet().getStartDateTime().plusMinutes(anotherMeetDuration);
            boolean bottomLine = meetStartTime.isAfter(startDateTime) && meetStartTime.isBefore(endDateTime);
            boolean upperLine = meetStartTime.plusMinutes(durationMinutes).isAfter(startDateTime) && meetStartTime.isBefore(endDateTime);
            if (bottomLine || upperLine) {
                return true;
            }
        }
        return false;
    }
}