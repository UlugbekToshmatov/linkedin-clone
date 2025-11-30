package com.linkedin.linkedin.models.user.repository;

import com.linkedin.linkedin.enums.Status;
import com.linkedin.linkedin.models.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmailAndStatus(String email, Status status);
    Boolean existsByEmail(String email);

    Optional<User> findByEmailAndStatusNot(String email, Status status);
    Optional<User> findByEmailAndStatus(String email, Status status);
}
