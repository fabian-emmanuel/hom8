package org.indulge.hom8.controllers;


import jakarta.validation.Valid;
import org.indulge.hom8.constants.Message;
import org.indulge.hom8.dtos.BaseResponse;
import org.indulge.hom8.dtos.OtpRequest;
import org.indulge.hom8.dtos.OtpValidationRequest;
import org.indulge.hom8.dtos.UserProfileDTO;
import org.indulge.hom8.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.indulge.hom8.constants.Message.OTP_SENT;
import static org.indulge.hom8.constants.Message.SUCCESSFUL;


@RestController
@RequestMapping(value = "/accounts")
public record AccountController(
        UserService userService
) {

    @PostMapping( "/validate")
    public Mono<BaseResponse<?>> validatePhone(@Valid @RequestBody OtpValidationRequest request) {
        return userService.validatePhone(request)
                .thenReturn(new BaseResponse<>(
                        true,
                        HttpStatus.OK,
                        Message.USER_ACCOUNT_ACTIVATED)
                );
    }

    @PostMapping( "/request-otp")
    public Mono<BaseResponse<?>> requestOtp(@Valid @RequestBody OtpRequest request) {
        return userService.requestOtp(request)
                .thenReturn(new BaseResponse<>(
                        true,
                        HttpStatus.OK,
                        OTP_SENT)
                );
    }

    @GetMapping( "/me")
    public Mono<BaseResponse<UserProfileDTO>> userProfile() {
        return userService.userProfile()
                .map(userProfile -> new BaseResponse<>(
                        true,
                        HttpStatus.OK,
                        SUCCESSFUL,
                        userProfile)
                );
    }

}
