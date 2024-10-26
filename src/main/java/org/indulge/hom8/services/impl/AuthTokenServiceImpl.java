package org.indulge.hom8.services.impl;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.indulge.hom8.constants.Message;
import org.indulge.hom8.constants.Security;
import org.indulge.hom8.dtos.LoginResponse;
import org.indulge.hom8.dtos.TokenResponse;
import org.indulge.hom8.exceptions.UnAuthorizedException;
import org.indulge.hom8.properties.JWT;
import org.indulge.hom8.services.AuthTokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;


@Slf4j
@Service
public record AuthTokenServiceImpl(
        JWT jwtProperty
) implements AuthTokenService {

    @Override
    public Mono<TokenResponse> generateAccessToken(String userPhone, String userType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Security.USER_ROLE, userType);
        Date now = new Date();
        Date validity = Date.from(Instant.now().plus(jwtProperty.expiration(), ChronoUnit.MILLIS));
        return Mono.fromCallable(() -> TokenResponse.builder()
                .token(createToken(claims, userPhone, now, validity))
                .expiresIn(String.valueOf(jwtProperty.expiration() / 1000))
                .build());
    }

    @Override
    public Mono<TokenResponse> generateRefreshToken(String userName){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", UUID.randomUUID().toString());
        Date now = new Date();
        Date validity = Date.from(Instant.now().plus(jwtProperty.expiration() * 3, ChronoUnit.MILLIS));
        return Mono.fromCallable(() -> TokenResponse.builder()
                .token(createToken(claims, userName, now, validity))
                .expiresIn(String.valueOf(jwtProperty.expiration() / 1000 * 3))
                .build());
    }


    @Override
    public Mono<LoginResponse> generateTokens(String userName, String userRole) {
        Mono<TokenResponse> accessTokenMono = generateAccessToken(userName, userRole);
        Mono<TokenResponse> refreshTokenMono = generateRefreshToken(userName);
        return Mono.zip(accessTokenMono, refreshTokenMono)
                .map(tuple -> LoginResponse.builder()
                        .accessToken(tuple.getT1().token())
                        .expiresIn(tuple.getT1().expiresIn())
                        .refreshToken(tuple.getT2().token())
                        .refreshExpiresIn(tuple.getT2().expiresIn())
                        .build());
    }

    @Override
    public Mono<String> getAuthenticatedUserPhone() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .flatMap(authentication -> {
                    if (authentication != null) {
                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                        return Mono.just(userDetails.getUsername());
                    } else {
                        return Mono.error(new UnAuthorizedException(Message.UNAUTHORIZED));
                    }
                })
                .doOnError(throwable -> {
            throw new UnAuthorizedException(Message.INVALID_EXPIRED_TOKEN);
        });
    }

    @Override
    public String extractUserPhoneFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Mono<String> extractTokenFromRequest(ServerWebExchange exchange) {
        String headerAuth = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.isEmpty(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return Mono.just(headerAuth.substring(7));
        }
        return Mono.empty();
    }

    @Override
    public boolean validateToken(String token, String authenticatedUserPhone) {
        final String username = extractUserPhoneFromToken(token);
        return (username.equals(authenticatedUserPhone) && !isTokenExpired(token));
    }

    public Boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private String createToken(Map<String, Object> claims, String subject, Date created, Date validity) {
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .claims(claims)
                .audience()
                .add("hom8-client")
                .and()
                .subject(subject)
                .issuedAt(created)
                .expiration(validity)
                .issuer("hom8")
                .signWith(getKey())
                .header()
                .add("typ", "JWT")
                .and()
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperty.secretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}
