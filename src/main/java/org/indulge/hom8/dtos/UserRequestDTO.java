package org.indulge.hom8.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.indulge.hom8.constants.Message;
import org.indulge.hom8.constants.Regex;
import org.indulge.hom8.validators.Phone;


@Builder
public record UserRequestDTO(

        @NotBlank(message = Message.FIRST_NAME_IS_REQUIRED)
        String firstName,

        @NotBlank(message = Message.LAST_NAME_IS_REQUIRED)
        String lastName,

        @Phone
        String phoneNumber,

        @NotBlank(message = Message.PIN_IS_REQUIRED)
        @Pattern(regexp = Regex.VALID_PIN, message = Message.INVALID_PIN)
        String pin
) {}