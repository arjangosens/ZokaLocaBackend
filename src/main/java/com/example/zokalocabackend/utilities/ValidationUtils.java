package com.example.zokalocabackend.utilities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class ValidationUtils
{
    public static <T> void validateEntity(T entity, Validator validator) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
