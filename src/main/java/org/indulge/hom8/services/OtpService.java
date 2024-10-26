package org.indulge.hom8.services;

import org.indulge.hom8.enums.Action;
import reactor.core.publisher.Mono;

public interface OtpService {
    Mono<Void> sendOTP(String phoneNumber, Action action);
    Mono<Void> validateOtp(String phoneNumber, String code);
}
