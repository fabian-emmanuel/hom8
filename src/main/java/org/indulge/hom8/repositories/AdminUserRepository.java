package org.indulge.hom8.repositories;

import org.indulge.hom8.models.Administrator;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AdminUserRepository extends R2dbcRepository<Administrator, Long> {
    Mono<Administrator> findByPhoneNumber(String phoneNumber);
    Mono<Boolean> existsByPhoneNumber(String phoneNumber);
}