package org.indulge.hom8.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.indulge.hom8.constants.Message;
import org.indulge.hom8.enums.Action;
import org.indulge.hom8.validators.Enum;
import org.indulge.hom8.validators.Phone;

@Builder
public record OtpRequest(
        @NotBlank(message = Message.PHONE_NUMBER_IS_REQUIRED)
        @Phone(message = Message.INVALID_PHONE_NUMBER)
        String phoneNumber,

        @NotBlank(message = Message.ACTION_IS_REQUIRED)
        @Enum(enumClass = Action.class)
        String action
) {}
