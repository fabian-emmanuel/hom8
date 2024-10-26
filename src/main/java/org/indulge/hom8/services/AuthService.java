package org.indulge.hom8.services;

import org.indulge.hom8.dtos.LoginRequest;
import org.indulge.hom8.dtos.LoginResponse;
import org.indulge.hom8.dtos.UserProfileDTO;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<LoginResponse> login(LoginRequest loginRequest);
    Mono<UserProfileDTO> authUser();
}
