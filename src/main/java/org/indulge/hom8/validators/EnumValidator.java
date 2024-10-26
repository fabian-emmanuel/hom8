package org.indulge.hom8.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public record EnumValidator() implements ConstraintValidator<Enum, String> {
    static Enum annotation;
    static Set<String> allowableValues;
    static boolean ignoreCase;
    static String message;
    static Object enumClass;
    static Object[] enumValues;

    @Override
    public void initialize(Enum annotation) {
        EnumListValidator.annotation = annotation;
        ignoreCase = annotation.ignoreCase();
        enumClass = annotation.enumClass();
        enumValues = annotation.enumClass().getEnumConstants();

        allowableValues = Arrays.stream(annotation.enumClass().getEnumConstants())
                .map(e -> ignoreCase ? e.name().toUpperCase() : e.name())
                .collect(Collectors.toSet());

        message = String.format("Invalid %s provided. Allowable Values: %s.",
                annotation.enumClass().getSimpleName(),
                String.join(", ", allowableValues));
    }

    @Override
    public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(valueForValidation)) {
            return true;
        }

        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                log.info("provided::{}; valueForValidation::{}", enumValue, valueForValidation);
                if (valueMatchesEnumValue(valueForValidation, enumValue)) {
                    return true;
                }
            }
        }

        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }

    private boolean valueMatchesEnumValue(String valueForValidation, Object enumValue) {
        String enumValueString = enumValue.toString();
        return ignoreCase
                ? valueForValidation.equalsIgnoreCase(enumValueString)
                : valueForValidation.equals(enumValueString);
    }
}
