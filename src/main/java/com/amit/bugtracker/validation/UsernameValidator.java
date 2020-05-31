package com.amit.bugtracker.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{3,16}$";

    @Override
    public boolean isValid(final String username, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        if (username == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
