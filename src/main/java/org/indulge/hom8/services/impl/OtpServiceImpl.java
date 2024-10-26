package org.indulge.hom8.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.indulge.hom8.enums.Action;
import org.indulge.hom8.exceptions.InvalidRequestException;
import org.indulge.hom8.exceptions.ResourceNotFoundException;
import org.indulge.hom8.hashes.Otp;
import org.indulge.hom8.properties.OTP;
import org.indulge.hom8.repositories.redis.OtpRepository;
import org.indulge.hom8.services.OtpService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.indulge.hom8.utils.SecurityUtil.generateRandomOtp;

@Slf4j
@Service
public record OtpServiceImpl(
        OtpRepository otpRepository,
        PasswordEncoder encoder,
        OTP otpProp
) implements OtpService {

    @Override
    public Mono<Void> sendOTP(String phoneNumber, Action action) {
        return otpRepository.findById(phoneNumber)
                .switchIfEmpty(createAndSaveOtp(phoneNumber, action))
                .flatMap(existingOtp -> Mono.error(new InvalidRequestException("OTP exist")));
    }

    @Override
    public Mono<Void> validateOtp(String phoneNumber, String code) {
        return otpRepository.findById(phoneNumber)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No OTP record found for " + phoneNumber)))
                .flatMap(otpRecord -> {
                    if (otpRecord.failedAttempts() >= otpProp.maxFailedAttempts()) {
                        return otpRepository.delete(phoneNumber)
                                .then(Mono.error(new InvalidRequestException("Max failed attempts exceeded.")));
                    }

                    if (!encoder.matches(code, otpRecord.code())) {
                        otpRecord = otpRecord.incrementFailedAttempts();
                        return otpRepository.save(otpRecord)
                                .then(Mono.error(new InvalidRequestException("Invalid OTP")));
                    }
                    return otpRepository.delete(phoneNumber).then();
                });
    }


    Mono<? extends Otp> createAndSaveOtp(String phoneNumber, Action action) {
        var code = generateRandomOtp();
        var otp = Otp.create(phoneNumber, encoder.encode(code), action);
        return otpRepository.save(otp)
                .then(sendOtp(phoneNumber, code)).thenReturn(otp);
    }

    private Mono<Void> sendOtp(String phoneNumber, String code) {
        log.info("Sending OTP `{}` to phoneNumber `{}`", code, phoneNumber);
        //TODO: YET TO BE IMPLEMENTED
        return Mono.empty();
    }
}
