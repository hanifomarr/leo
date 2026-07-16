package com.selloohub.leo.admin;

import com.selloohub.leo.admin.model.Admin;
import com.selloohub.leo.admin.model.AdminRole;
import com.selloohub.leo.admin.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.admin.bootstrap.enabled", havingValue = "true")
public class AdminBootstrapRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminBootstrapRunner.class);
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final String bootstrapUsername;
    private final String bootstrapPassword;

    public AdminBootstrapRunner(
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.admin.bootstrap.username}") String bootstrapUsername,
            @Value("${app.admin.bootstrap.password}") String bootstrapPassword) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.bootstrapUsername = bootstrapUsername;
        this.bootstrapPassword = bootstrapPassword;
    }

    @Override
    public void run(String... args) throws Exception {

        if (adminRepository.existsByUsername(bootstrapUsername)) {
            log.info("Admin bootstrap skipped — username '{}' already exists", bootstrapUsername);
            return;
        }

        String passwordHash = passwordEncoder.encode(bootstrapPassword);

        Admin admin = new Admin(bootstrapUsername, passwordHash, "Admin", AdminRole.SUPER_ADMIN);

        adminRepository.save(admin);
        log.info("Admin created: {}", admin.getUsername());
    }
}
