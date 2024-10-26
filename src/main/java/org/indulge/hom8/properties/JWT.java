package org.indulge.hom8.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JWT(
        String secretKey,
        Long expiration
) {}
