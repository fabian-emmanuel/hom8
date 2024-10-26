package org.indulge.hom8.repositories;

import org.indulge.hom8.models.Helper;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface HelperRepository  extends R2dbcRepository<Helper, Long> {
    Mono<Helper> findByPhoneNumber(String phoneNumber);
    Mono<Boolean> existsByPhoneNumber(String phoneNumber);
}
