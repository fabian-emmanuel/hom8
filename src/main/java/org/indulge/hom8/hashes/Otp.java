package org.indulge.hom8.hashes;


import lombok.Builder;
import org.indulge.hom8.enums.Action;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.time.LocalDateTime;

import static org.indulge.hom8.constants.Schema.HASH_OTP;

@Builder
@RedisHash(value = HASH_OTP, timeToLive = 600L)
public record Otp(
        @Id
        String id,
        String code,
        Action action,
        Integer failedAttempts,
        LocalDateTime createdAt
) {
        public static Otp create(String phoneNumber, String code, Action action) {
                return new Otp(
                        phoneNumber,
                        code,
                        action,
                        0,
                        LocalDateTime.now()
                );
        }

        public Otp incrementFailedAttempts() {
                return new Otp(
                        id,
                        code,
                        action,
                        failedAttempts + 1,
                        createdAt
                );
        }
}
