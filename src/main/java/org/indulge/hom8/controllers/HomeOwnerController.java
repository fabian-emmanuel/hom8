package org.indulge.hom8.controllers;

import jakarta.validation.Valid;
import org.indulge.hom8.dtos.BaseResponse;
import org.indulge.hom8.dtos.UserRequestDTO;
import org.indulge.hom8.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.indulge.hom8.constants.Message.SUCCESSFUL;

@RestController
@RequestMapping(value = "/home-owners")
public record HomeOwnerController(
        UserService userService

) {
    @PostMapping( "/register")
    public Mono<BaseResponse<?>> registerHomeOwner(@Valid @RequestBody UserRequestDTO requestDTO) {
        return userService.registerHomeOwner(requestDTO)
                .map(userResponse -> new BaseResponse<>(
                        true,
                        HttpStatus.OK,
                        SUCCESSFUL,
                        userResponse)
                );
    }
}