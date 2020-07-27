package com.amit.bugtracker.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = NameValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidName {

    String message() default "Invalid name - Must contain 2 to 50 english letters only.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

