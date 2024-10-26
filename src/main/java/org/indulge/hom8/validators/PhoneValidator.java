package org.indulge.hom8.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public record PhoneValidator(
//        String regexp
) implements ConstraintValidator<Phone, String> {

     static String regexp;
     static boolean required;

    @Override
    public void initialize(Phone constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
        regexp = constraintAnnotation.regexp();
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            // If required, check for null or empty
            if (phoneNumber == null || phoneNumber.isEmpty()) {
                return false; // Invalid if required and missing
            }
        } else {
            // If not required, allow null or empty values
            if (phoneNumber == null || phoneNumber.isEmpty()) {
                return true; // Valid if not required
            }
        }
        return phoneNumber.matches(regexp);
    }
}
