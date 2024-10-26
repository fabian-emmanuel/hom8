package org.indulge.hom8.repositories.redis;

import lombok.extern.slf4j.Slf4j;
import org.indulge.hom8.hashes.Otp;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.indulge.hom8.constants.Schema.HASH_OTP;


@Slf4j
@Service
public record OtpRepository(
        ReactiveHashOperations<String, String, Otp> hashOps
) {

    public Mono<Otp> findById(String id) {
        return hashOps.get(HASH_OTP, id).flatMap(Mono::just);
    }


    public Mono<Boolean> save(Otp otp) {
        return hashOps.put(HASH_OTP, otp.id(), otp);
    }

    public Mono<Boolean> delete(String id) {
        return hashOps.delete(id);
    }
}
