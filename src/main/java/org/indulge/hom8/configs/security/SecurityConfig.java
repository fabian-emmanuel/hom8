package org.indulge.hom8.configs.security;

import lombok.extern.slf4j.Slf4j;
import org.indulge.hom8.constants.Security;
import org.indulge.hom8.exceptions.CustomAccessDeniedExceptionHandler;
import org.indulge.hom8.exceptions.CustomAuthenticationEntryPointHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public record SecurityConfig(
        CustomAccessDeniedExceptionHandler unauthorizedExceptionHandler,
        CustomAuthenticationEntryPointHandler authenticationEntryPointHandler,
        CustomSecurityContextRepository customSecurityContextRepository
) {

    @Bean
    public SecurityWebFilterChain webSecurityFilter(ServerHttpSecurity http) {
        return http.cors(ServerHttpSecurity.CorsSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(Security.WHITE_LISTED_PATH.toArray(String[]::new))
                        .permitAll()
                        .anyExchange()
                        .authenticated())
                .securityContextRepository(customSecurityContextRepository)
                .exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec
                        .authenticationEntryPoint(authenticationEntryPointHandler)
                        .accessDeniedHandler(unauthorizedExceptionHandler)
                ).logout(logoutSpec -> logoutSpec.logoutUrl("/logout"))
                .build();
    }
}
