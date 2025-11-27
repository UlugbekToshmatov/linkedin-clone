package com.linkedin.linkedin.features.authentication.configuration;

import com.linkedin.linkedin.features.authentication.enums.Status;
import com.linkedin.linkedin.features.authentication.model.AuthenticationUser;
import com.linkedin.linkedin.features.authentication.repository.AuthenticationRepository;
import com.linkedin.linkedin.features.authentication.utils.Encoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public CommandLineRunner initDatabase(AuthenticationRepository repository) {
        return args -> {
            if (!repository.existsByEmailAndStatus("admin@linkedin.com", Status.ACTIVE)) {
                AuthenticationUser adminUser = new AuthenticationUser();
                adminUser.setEmail("admin@linkedin.com");
                adminUser.setPassword(Encoder.encode("admin"));
                adminUser.setStatus(Status.ACTIVE);

                repository.save(adminUser);
            }
        };
    }
}
