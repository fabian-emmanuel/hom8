package org.indulge.hom8.dtos;

import lombok.Builder;

@Builder
public record LoginResponse(
        String accessToken,
        String expiresIn,
        String refreshToken,
        String refreshExpiresIn
) {
}
