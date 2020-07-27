package com.amit.bugtracker.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<ValidName, String> {

    private static final String NAME_PATTERN = "^[a-zA-Z ,.'-]{2,50}+$";

    @Override
    public boolean isValid(final String username, ConstraintValidatorContext context) {
        if (username == null || username.isEmpty())
            return false;

        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(username);

        return matcher.matches();
    }


}
