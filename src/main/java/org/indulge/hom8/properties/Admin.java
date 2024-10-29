package org.indulge.hom8.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "admin")
public record Admin(
        String phoneNumber,
        String pin
) {}
