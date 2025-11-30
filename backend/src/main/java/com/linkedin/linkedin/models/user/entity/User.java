package com.linkedin.linkedin.models.user.entity;

import com.linkedin.linkedin.base_classes.BaseEntity;
import com.linkedin.linkedin.enums.Role;
import com.linkedin.linkedin.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "users")
@Entity
public class User extends BaseEntity {
    @Id
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role = Role.USER;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.PENDING_VERIFICATION;

    @Column(name = "image_url")
    private String imageUrl = "https://cdn-icons-png.flaticon.com/512/149/149071.png";
}
