package org.indulge.hom8.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api")
public record Api(
        String version,
        String name,
        String email,
        String url
) {}
