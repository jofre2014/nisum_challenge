package com.nisum.challenge.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@ConfigurationProperties(prefix = "custom.password")
public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    private String regex;
    private Pattern pattern;

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password != null && pattern.matcher(password).matches();
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
}
