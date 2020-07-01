package ru.grandstep.meeting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.grandstep.meeting.dto.MeetDataDto;
import ru.grandstep.meeting.dto.MeetDto;
import ru.grandstep.meeting.dto.UserMeetDto;
import ru.grandstep.meeting.model.Meet;
import ru.grandstep.meeting.model.UserInMeet;
import ru.grandstep.meeting.service.MeetService;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MeetService meetService;

    @PostMapping("createMeet")
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> createMeet(@RequestBody MeetDataDto meetDataDto) throws MessagingException {
        Meet meet = new Meet(meetDataDto.getName(), meetDataDto.getStartDate(), meetDataDto.getDuration());
        return meetService.save(meet, meetDataDto.getLogins(), meetDataDto.getCreatorLogin());
    }

    @PostMapping("deleteMeet")
    public void deleteMeet(@RequestBody MeetDto meetDto) {
        meetService.delete(meetDto.getName());
    }

    @PostMapping("addUserToMeet")
    public String addUserToMeet(@RequestBody UserMeetDto userMeetDto) throws MessagingException {
        return meetService.addUser(userMeetDto.getLogin(), userMeetDto.getMeetName());
    }

    @PostMapping("deleteUserFromMeet")
    public void deleteUserFromMeet(@RequestBody UserMeetDto userMeetDto) {
        meetService.deleteUser(userMeetDto.getMeetName(), userMeetDto.getLogin());
    }

    @PostMapping("getMeets")
    public List<MeetDataDto> getMeets() {
        return meetService.getAll();
    }
}
