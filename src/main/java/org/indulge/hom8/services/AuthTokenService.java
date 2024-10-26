package org.indulge.hom8.services;

import org.indulge.hom8.dtos.LoginResponse;
import org.indulge.hom8.dtos.TokenResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface AuthTokenService {
    Mono<TokenResponse> generateAccessToken(String userName, String userType);
    Mono<TokenResponse> generateRefreshToken(String userName);
    Mono<LoginResponse> generateTokens(String userName, String userRole);
    Mono<String> getAuthenticatedUserPhone();
    String extractUserPhoneFromToken(String token);
    Mono<String> extractTokenFromRequest(ServerWebExchange exchange);
    boolean validateToken(String token, String authenticatedUserPhone);
}
