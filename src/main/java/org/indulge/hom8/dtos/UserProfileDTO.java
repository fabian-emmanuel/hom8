package org.indulge.hom8.dtos;

import lombok.Builder;
import org.indulge.hom8.enums.UserType;

@Builder
public record UserProfileDTO(
        String phoneNumber,
        String firstName,
        String lastName,
        UserType userType,
        boolean active
) {}
