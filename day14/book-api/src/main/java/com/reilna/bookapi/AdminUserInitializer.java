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
    private final String readerUsername;
    private final String readerPassword;

    public AdminUserInitializer(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder,
            @Value("${security.admin.username}") String adminUsername,
            @Value("${security.admin.password}") String adminPassword,
            @Value("${security.reader.username}") String readerUsername,
            @Value("${security.reader.password}") String readerPassword) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
        this.readerUsername = readerUsername;
        this.readerPassword = readerPassword;
    }

    @Override
    public void run(String... args) {
        createUser(adminUsername, adminPassword, "ADMIN");
        createUser(readerUsername, readerPassword, "USER");
    }

    private void createUser(String username, String password, String role) {
        if (appUserRepository.findByUsername(username).isEmpty()) {
            AppUser user = new AppUser(
                    null,
                    username,
                    passwordEncoder.encode(password),
                    role
            );

            appUserRepository.save(user);
        }
    }
}
