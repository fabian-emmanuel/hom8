package org.indulge.hom8.controllers;


import jakarta.validation.Valid;
import org.indulge.hom8.dtos.BaseResponse;
import org.indulge.hom8.dtos.LoginRequest;
import org.indulge.hom8.dtos.LoginResponse;
import org.indulge.hom8.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.indulge.hom8.constants.Message.SUCCESSFUL;


@RestController
@RequestMapping(value = "/auth")
public record AuthController(
        AuthService authService
) {

    @PostMapping( "/login")
    public Mono<BaseResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest)
                .map(loginResponse -> new BaseResponse<>(
                        true,
                        HttpStatus.OK,
                        SUCCESSFUL,
                        loginResponse)
                );
    }
}
