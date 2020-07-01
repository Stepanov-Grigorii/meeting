package ru.grandstep.meeting.constraint.validator;

import ru.grandstep.meeting.constraint.annotation.DateConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public class DateValidator implements
        ConstraintValidator<DateConstraint, Temporal> {
    @Override
    public void initialize(DateConstraint contactDate) {
    }

    @Override
    public boolean isValid(Temporal contactField,
                           ConstraintValidatorContext cxt) {
        if (contactField == null) {
            return false;
        }
        LocalDateTime fieldValue = LocalDateTime.from(contactField);
        return LocalDateTime.now().isBefore(fieldValue);
    }
}