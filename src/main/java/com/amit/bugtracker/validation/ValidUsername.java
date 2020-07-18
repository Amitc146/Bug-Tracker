package com.amit.bugtracker.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidUsername {

    String message() default "Invalid username - Must contain 3 to 16 letters/numbers.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
