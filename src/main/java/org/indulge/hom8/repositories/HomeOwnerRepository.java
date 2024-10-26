package org.indulge.hom8.repositories;

import org.indulge.hom8.models.HomeOwner;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface HomeOwnerRepository extends R2dbcRepository<HomeOwner, Long> {
    Mono<HomeOwner> findByPhoneNumber(String phoneNumber);
    Mono<Boolean> existsByPhoneNumber(String phoneNumber);
}
