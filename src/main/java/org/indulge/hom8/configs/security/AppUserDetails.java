package org.indulge.hom8.configs.security;

import lombok.extern.slf4j.Slf4j;
import org.indulge.hom8.exceptions.ResourceNotFoundException;
import org.indulge.hom8.repositories.HelperRepository;
import org.indulge.hom8.repositories.HomeOwnerRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public record AppUserDetails(
        HomeOwnerRepository homeOwnerRepository,
        HelperRepository helperRepository
) implements ReactiveUserDetailsService {

    @Override
    public Mono<UserDetails> findByUsername(String phoneNumber) {
        return homeOwnerRepository.findByPhoneNumber(phoneNumber)
                .map(u -> User.withUsername(u.getPhoneNumber())
                        .password(u.getPin())
                        .accountExpired(!u.isActive())
                        .disabled(!u.isActive())
                        .accountLocked(!u.isActive())
                        .roles(u.getUserType().name())
                        .build())
                .switchIfEmpty(helperRepository.findByPhoneNumber(phoneNumber)
                        .map(u -> User.withUsername(u.getPhoneNumber())
                                .password(u.getPin())
                                .accountExpired(!u.isActive())
                                .disabled(!u.isActive())
                                .accountLocked(!u.isActive())
                                .roles(u.getUserType().name())
                                .build())
                        .switchIfEmpty(Mono.error(new ResourceNotFoundException("username/password not found."))));
    }
}