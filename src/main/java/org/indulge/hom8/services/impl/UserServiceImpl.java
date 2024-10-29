package org.indulge.hom8.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.indulge.hom8.constants.Message;
import org.indulge.hom8.dtos.*;
import org.indulge.hom8.enums.Action;
import org.indulge.hom8.enums.UserType;
import org.indulge.hom8.exceptions.DuplicateException;
import org.indulge.hom8.exceptions.ResourceNotFoundException;
import org.indulge.hom8.mappers.UserMapper;
import org.indulge.hom8.models.Helper;
import org.indulge.hom8.models.HomeOwner;
import org.indulge.hom8.models.User;
import org.indulge.hom8.repositories.HelperRepository;
import org.indulge.hom8.repositories.HomeOwnerRepository;
import org.indulge.hom8.services.AuthService;
import org.indulge.hom8.services.OtpService;
import org.indulge.hom8.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Slf4j
@Service
public record UserServiceImpl(
        HomeOwnerRepository homeOwnerRepository,
        HelperRepository helperRepository,
        UserMapper userMapper,
        PasswordEncoder encoder,
        OtpService otpService,
        AuthService authService
) implements UserService {

    @Override
    public Mono<UserResponseDTO> registerHelper(UserRequestDTO requestDTO) {
        return checkPhoneNumberExists(requestDTO.phoneNumber())
                .then(Mono.defer(() -> {
                    var user = userMapper.toHelper(requestDTO, encoder, UserType.HELPER);
                    return helperRepository.save(user)
                            .flatMap(savedUser -> otpService.sendOTP(requestDTO.phoneNumber(), Action.REGISTRATION)
                                    .thenReturn(savedUser))
                            .map(userMapper::toUserResponseDTO);
                }));
    }

    @Override
    public Mono<UserResponseDTO> registerHomeOwner(UserRequestDTO requestDTO) {
        return checkPhoneNumberExists(requestDTO.phoneNumber())
                .then(Mono.defer(() -> {
                    var user = userMapper.toHomeOwner(requestDTO, encoder, UserType.HOME_OWNER);
                    return homeOwnerRepository.save(user)
                            .flatMap(savedUser -> otpService.sendOTP(requestDTO.phoneNumber(), Action.REGISTRATION)
                                    .thenReturn(savedUser))
                            .map(userMapper::toUserResponseDTO);
                }));
    }

    @Override
    public Mono<Void> validatePhone(OtpValidationRequest request) {
        return switch (UserType.valueOf(request.userType())) {
            case HOME_OWNER -> getHomeOwner(request.phoneNumber())
                    .flatMap(user -> validateOtpAndActivate(user, request));
            case HELPER -> getHelper(request.phoneNumber())
                    .flatMap(user -> validateOtpAndActivate(user, request));
            case SUPER_ADMIN, ADMIN -> null;
        };
    }

    @Override
    public Mono<Void> requestOtp(OtpRequest request) {
        return otpService.sendOTP(request.phoneNumber(), Action.valueOf(request.action()));
    }

    @Override
    public Mono<UserProfileDTO> userProfile() {
        return authService.authUser();
    }

    private Mono<Void> checkPhoneNumberExists(String phoneNumber) {
        return Mono.zip(homeOwnerRepository.existsByPhoneNumber(phoneNumber), helperRepository.existsByPhoneNumber(phoneNumber))
                .map(tuple -> tuple.getT1() || tuple.getT2())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new DuplicateException(String.format(Message.USER_WITH_PHONE_NUMBER_ALREADY_EXIST, phoneNumber)));
                    }
                    return otpService.sendOTP(phoneNumber, Action.REGISTRATION);
                });
    }

    private <T extends User> Mono<Void> validateOtpAndActivate(T user, OtpValidationRequest request) {
        return otpService.validateOtp(request.phoneNumber(), request.code())
                .doOnSuccess(ignored -> activateUserAccount(user))
                .then();
    }

    private <T extends User> void activateUserAccount(T user) {
        user.setActive(true);
        if (user instanceof Helper helper) {
            helperRepository.save(helper).subscribe();
        } else if (user instanceof HomeOwner homeOwner) {
            homeOwnerRepository.save(homeOwner).subscribe();
        }
    }

    private Mono<Helper> getHelper(String phoneNumber) {
        return getUserByPhoneNumber(helperRepository::findByPhoneNumber, phoneNumber, UserType.HELPER);
    }

    private Mono<HomeOwner> getHomeOwner(String phoneNumber) {
        return getUserByPhoneNumber(homeOwnerRepository::findByPhoneNumber, phoneNumber, UserType.HOME_OWNER);
    }

    private <T> Mono<T> getUserByPhoneNumber(Function<String, Mono<T>> findByPhoneNumberFunction,
                                             String phoneNumber,
                                             UserType userType) {
        return findByPhoneNumberFunction.apply(phoneNumber)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(userType + " with phone number " + phoneNumber + " does not exist")));
    }

}
