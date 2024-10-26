package org.indulge.hom8.dtos;

import lombok.Builder;

@Builder
public record TokenResponse(
        String token,
        String expiresIn
) {}
