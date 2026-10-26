package com.reilna.bookapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminUsername;
    private final String adminPassword;

    public AdminUserInitializer(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder,
            @Value("${security.admin.username}") String adminUsername,
            @Value("${security.admin.password}") String adminPassword) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(String... args) {
        if (appUserRepository.findByUsername(adminUsername).isEmpty()) {
            AppUser admin = new AppUser(
                    null,
                    adminUsername,
                    passwordEncoder.encode(adminPassword),
                    "ADMIN"
            );

            appUserRepository.save(admin);
        }
    }
}
