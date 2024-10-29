package org.indulge.hom8.configs.security;

import lombok.extern.slf4j.Slf4j;
import org.indulge.hom8.exceptions.ResourceNotFoundException;
import org.indulge.hom8.repositories.AdminUserRepository;
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
        HelperRepository helperRepository,
        AdminUserRepository adminUserRepository
) implements ReactiveUserDetailsService {

    @Override
    public Mono<UserDetails> findByUsername(String phoneNumber) {
        return adminUserRepository.findByPhoneNumber(phoneNumber)
                .map(this::mapToUserDetails)
                .switchIfEmpty(homeOwnerRepository.findByPhoneNumber(phoneNumber)
                .map(this::mapToUserDetails)
                .switchIfEmpty(helperRepository.findByPhoneNumber(phoneNumber)
                        .map(this::mapToUserDetails)
                        .switchIfEmpty(Mono.error(new ResourceNotFoundException("username/password not found.")))));
    }

    private UserDetails mapToUserDetails(org.indulge.hom8.models.User user) {
        return User.withUsername(user.getPhoneNumber())
                .password(user.getPin())
                .accountExpired(!user.isActive())
                .disabled(!user.isActive())
                .accountLocked(!user.isActive())
                .roles(user.getUserType().name())
                .build();
    }
}
