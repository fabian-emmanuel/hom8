package org.indulge.hom8.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.data.redis")
public record Redis(
        String host,
        int port,
        int database
) {}
