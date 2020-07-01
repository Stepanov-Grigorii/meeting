package ru.grandstep.meeting.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MeetDataDto {
    //TODO validation
    private String name;
    private LocalDateTime startDate;
    private List<String> logins;
    private Integer duration;
    private String creatorLogin;
}
