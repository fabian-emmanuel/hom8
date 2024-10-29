package org.indulge.hom8.configs.app;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.indulge.hom8.enums.UserType;
import org.indulge.hom8.models.Administrator;
import org.indulge.hom8.properties.Admin;
import org.indulge.hom8.repositories.AdminUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Slf4j
@Component
public record AdminInitializer(
        Admin admin,
        AdminUserRepository adminRepository,
        PasswordEncoder encoder
) {

    @PostConstruct
    public void initializeAdmin() {
        adminRepository.existsByPhoneNumber(admin.phoneNumber())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.empty();
                    }
                    return createDefaultAdmin();
                })
                .subscribe();
    }

    private Mono<Void> createDefaultAdmin() {
        Administrator adminUser = Administrator.builder()
                .firstName("Super")
                .lastName("Admin")
                .phoneNumber(admin().phoneNumber())
                .pin(encoder.encode(admin().pin()))
                .active(true)
                .userType(UserType.SUPER_ADMIN)
                .build();
        return adminRepository.save(adminUser)
                .doOnSuccess(user -> log.info("Default adminUser user created."))
                .then();
    }
}
