package com.validations;

import com.annotations.PasswordMatches;
import com.payload.request.SignUpRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchersValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        SignUpRequest userSignUpRequest =(SignUpRequest) o;
        return userSignUpRequest.getPassword().equals(userSignUpRequest.getConfirmPassword());
    }
}
