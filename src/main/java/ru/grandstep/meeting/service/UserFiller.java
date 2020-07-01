package ru.grandstep.meeting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.grandstep.meeting.model.User;
import ru.grandstep.meeting.repository.UserRepository;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class UserFiller {

    final UserRepository userRepository;

    @PostConstruct
    public void initUsers(){
        if(userRepository.count() == 0){
            userRepository.save(new User("Login1", "Qwerty123", "stegrand@yandex.ru"));
            userRepository.save(new User("Login2", "Qwerty123", "stegrand@yandex.ru"));
            userRepository.save(new User("Login3", "Qwerty123", "stegrand@yandex.ru"));
            userRepository.save(new User("Login4", "Qwerty123", "stegrand@yandex.ru"));
            userRepository.save(new User("Login5", "Qwerty123", "stegrand@yandex.ru"));
            userRepository.save(new User("Login6", "Qwerty123", "stegrand@yandex.ru"));
        }
    }

}
