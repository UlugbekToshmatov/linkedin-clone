package com.linkedin.linkedin.models.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.linkedin.linkedin.enums.Role;
import com.linkedin.linkedin.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;
    private Role role;
    private Status status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
