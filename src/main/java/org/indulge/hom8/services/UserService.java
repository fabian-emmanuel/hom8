package org.indulge.hom8.services;

import org.indulge.hom8.dtos.*;
import reactor.core.publisher.Mono;


public interface UserService {
    Mono<UserResponseDTO> registerHomeOwner(UserRequestDTO requestDTO);
    Mono<UserResponseDTO> registerHelper(UserRequestDTO requestDTO);
    Mono<Void> validatePhone(OtpValidationRequest request);
    Mono<Void> requestOtp(OtpRequest request);
    Mono<UserProfileDTO> userProfile();
}
