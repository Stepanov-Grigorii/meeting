package ru.grandstep.meeting.exception.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.grandstep.meeting.exception.EntityNotFoundException;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
@RestControllerAdvice
@Slf4j
public class EntityExceptionAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String entityNotFoundExceptionHandler(EntityNotFoundException e) {
        log.warn(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(RollbackException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> entityBadReqException(RollbackException e) {
        if (e.getCause() instanceof ConstraintViolationException cause) {
            List<String> messages = cause.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());
            log.warn(String.valueOf(messages));
            return messages;
        }
        log.error( e.getMessage(), e);
        return List.of(e.getMessage());
    }
}
