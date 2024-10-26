package org.indulge.hom8.dtos;

import lombok.Builder;

@Builder
public record UserResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String phoneNumber,
        boolean active,
        String createdAt,
        String userType
) {}
