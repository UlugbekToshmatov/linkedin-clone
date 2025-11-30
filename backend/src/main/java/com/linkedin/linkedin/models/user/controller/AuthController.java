package com.linkedin.linkedin.models.user.controller;

import com.linkedin.linkedin.base_classes.HttpResponse;
import com.linkedin.linkedin.models.user.dto.auth.LoginRequest;
import com.linkedin.linkedin.models.user.dto.auth.LoginResponse;
import com.linkedin.linkedin.models.user.dto.auth.RegistrationRequest;
import com.linkedin.linkedin.models.user.dto.UserResponse;
import com.linkedin.linkedin.models.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/")
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("user");
    }

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(
        @RequestBody @Valid RegistrationRequest registrationRequest
    ) {
        UserResponse registeredUser = authService.register(registrationRequest);

        return ResponseEntity.ok(
            HttpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .description(HttpStatus.OK.name())
                .data(Map.of("registeredUser", registeredUser))
                .build()
        );
    }

    @GetMapping("/verify-account")
    public ResponseEntity<HttpResponse> verifyAccount(
        @RequestParam String token
    ) {
        authService.verifyAccount(token);

        return ResponseEntity.ok(
            HttpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .description(HttpStatus.OK.name())
                .data(Map.of("message", "Account verified successfully"))
                .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(
        @RequestBody @Valid LoginRequest loginRequest
    ) {
        LoginResponse loginResponse = authService.login(loginRequest);

        return ResponseEntity.ok(
            HttpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .description(HttpStatus.OK.name())
                .data(Map.of("response", loginResponse))
                .build()
        );
    }
}
