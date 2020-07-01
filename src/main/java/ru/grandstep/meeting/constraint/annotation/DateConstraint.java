package ru.grandstep.meeting.constraint.annotation;

import ru.grandstep.meeting.constraint.validator.DateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Аннотация валидирует поле {@link java.time.temporal.Temporal} <br>
 * Валидными считаются значения {@code !null} и даты в промежутке от 01.01.2018 до текущего момента.
 *
 * @author grandstep
 */
@Documented
@Constraint(validatedBy = DateValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateConstraint {
    String message() default "Invalid date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
