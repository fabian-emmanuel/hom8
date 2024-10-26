package org.indulge.hom8.configs.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import lombok.RequiredArgsConstructor;
import org.indulge.hom8.properties.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


@Configuration
@RequiredArgsConstructor
public class OpenApi {
    private final Api api;

    @Bean
    public OpenAPI customOpenAPI() {
        final String apiTitle = String.format("%s API", StringUtils.capitalize(api.name()));
        final String apiDescription = String.format("API for %s. Contains public endpoints.", api.name());
        final Contact apiContact = new Contact()
                .name(api.name())
                .url(api.url())
                .email(api.email());

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("jwt", createApiSecurityScheme()))
                .addSecurityItem(new SecurityRequirement().addList("jwt"))
                .info(new Info()
                        .title(apiTitle)
                        .description(apiDescription)
                        .termsOfService("Terms of service")
                        .contact(apiContact)
                        .version(api.version()));
    }

    private SecurityScheme createApiSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Enter your JWT token");
    }
}
