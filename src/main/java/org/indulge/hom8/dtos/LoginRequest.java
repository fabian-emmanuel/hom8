package org.indulge.hom8.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.indulge.hom8.constants.Message;
import org.indulge.hom8.constants.Regex;
import org.indulge.hom8.validators.Phone;

@Builder
public record LoginRequest(
        @NotBlank(message = Message.PHONE_NUMBER_IS_REQUIRED)
        @Phone(message = Message.INVALID_PHONE_NUMBER)
        String phoneNumber,

        @NotBlank(message = Message.PIN_IS_REQUIRED)
        @Pattern(regexp = Regex.VALID_PIN, message = Message.INVALID_PIN)
        String pin
) {}
