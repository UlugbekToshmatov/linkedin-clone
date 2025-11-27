package com.linkedin.linkedin.features.authentication.repository;

import com.linkedin.linkedin.features.authentication.enums.Status;
import com.linkedin.linkedin.features.authentication.model.AuthenticationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends JpaRepository<AuthenticationUser, Long> {
    Boolean existsByEmailAndStatus(String email, Status status);
}
