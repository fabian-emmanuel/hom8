package org.indulge.hom8.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "otp")
public record OTP(
        Integer maxFailedAttempts
) {}
