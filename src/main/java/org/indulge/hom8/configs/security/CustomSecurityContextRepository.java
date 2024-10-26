package org.indulge.hom8.configs.security;

import lombok.extern.slf4j.Slf4j;
import org.indulge.hom8.constants.Message;
import org.indulge.hom8.exceptions.InvalidRequestException;
import org.indulge.hom8.services.AuthTokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;



@Slf4j
@Component
public record CustomSecurityContextRepository(
        AuthTokenService authTokenService,
        AppUserDetails appUserDetails
) implements ServerSecurityContextRepository {

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return authTokenService.extractTokenFromRequest(exchange)
                .flatMap(authToken -> {
                    String userName = authTokenService.extractUserPhoneFromToken(authToken);

                    if (!authTokenService.validateToken(authToken, userName)) {
                        return Mono.error(new InvalidRequestException(Message.INVALID_EXPIRED_TOKEN));
                    }

                    return appUserDetails.findByUsername(userName)
                            .flatMap(this::authenticateUser)
                            .map(SecurityContextImpl::new)
                            .doOnError(throwable -> log.error("CustomSecurityContextRepository::ErrorMsg -> {}", throwable.getMessage()));
                });
    }

    private Mono<Authentication> authenticateUser(UserDetails userDetails) {
        return Mono.just(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
    }
}
