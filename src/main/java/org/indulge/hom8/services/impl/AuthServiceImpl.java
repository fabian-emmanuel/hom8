package org.indulge.hom8.services.impl;

import org.indulge.hom8.dtos.LoginRequest;
import org.indulge.hom8.dtos.LoginResponse;
import org.indulge.hom8.dtos.UserProfileDTO;
import org.indulge.hom8.exceptions.InvalidRequestException;
import org.indulge.hom8.exceptions.UnAuthorizedException;
import org.indulge.hom8.mappers.UserMapper;
import org.indulge.hom8.repositories.HelperRepository;
import org.indulge.hom8.repositories.HomeOwnerRepository;
import org.indulge.hom8.services.AuthService;
import org.indulge.hom8.services.AuthTokenService;
import org.indulge.hom8.utils.SecurityUtil;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public record AuthServiceImpl(
        ReactiveAuthenticationManager authenticationManager,
        AuthTokenService authTokenService,
        HomeOwnerRepository homeOwnerRepository,
        HelperRepository helperRepository,
        UserMapper userMapper
) implements AuthService {


    @Override
    public Mono<LoginResponse> login(LoginRequest loginRequest) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.phoneNumber(), loginRequest.pin()))
                .flatMap(authentication -> authTokenService.generateTokens(authentication.getName(), SecurityUtil.extractUserRole(authentication)))
                .doOnError(throwable -> {
                    throw new InvalidRequestException(throwable.getMessage());
                });
    }

    @Override
    public Mono<UserProfileDTO> authUser() {
        return authTokenService.getAuthenticatedUserPhone()
                .flatMap(phoneNumber -> homeOwnerRepository.findByPhoneNumber(phoneNumber)
                        .map(userMapper::userProfile)
                        .switchIfEmpty(helperRepository.findByPhoneNumber(phoneNumber)
                                .map(userMapper::userProfile)
                        .switchIfEmpty(Mono.error(new UnAuthorizedException("Unauthorized User!")))
                ));
    }

}
