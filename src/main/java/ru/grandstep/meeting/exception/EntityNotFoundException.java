package ru.grandstep.meeting.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String entityName, Integer id) {
        super(entityName + " with id = " + id + " not found");
    }
}
