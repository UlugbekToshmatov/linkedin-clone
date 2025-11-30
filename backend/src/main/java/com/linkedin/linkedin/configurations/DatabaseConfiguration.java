package com.linkedin.linkedin.configurations;

import com.linkedin.linkedin.enums.Role;
import com.linkedin.linkedin.enums.Status;
import com.linkedin.linkedin.models.user.entity.User;
import com.linkedin.linkedin.models.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DatabaseConfiguration {
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            if (!repository.existsByEmail("admin@linkedin.com")) {
                User admin = new User();
                admin.setFirstName("Admin");
                admin.setLastName("Admin");
                admin.setEmail("admin@linkedin.com");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setStatus(Status.ACTIVE);
                admin.setRole(Role.ADMIN);

                repository.save(admin);
            }
        };
    }
}
