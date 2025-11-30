package com.linkedin.linkedin.utils;

import com.linkedin.linkedin.models.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.linkedin.linkedin.jwt.JwtProviderParams.ACCOUNT_VERIFICATION_TOKEN_EXPIRATION;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;


    public void sendEmail(Map<String, String> payload) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(payload.get("email"));
        mailMessage.setSubject(payload.get("subject"));
        mailMessage.setText(payload.get("content"));

        try {
            CompletableFuture.runAsync(() -> mailSender.send(mailMessage));
            log.info("Sent email details: {}", mailMessage);
        } catch (Exception e) {
            log.error("Failed to send email: {}", e.getMessage());
            throw e;
        }
    }

    public Map<String, String> getAccountVerificationPayload(User user, String link) {
        return Map.of(
            "email", user.getEmail(),
            "subject", "Email Confirmation",
            "content", String.format("""
                    Hello %s %s,\
                    
                    Please, confirm your email address by clicking the link below:
                    %s\
                    
                    Note that the link expires in %s minutes.\
                    
                    Best regards,
                    LinkedIn Team""",
                user.getFirstName(), user.getLastName(), link, ACCOUNT_VERIFICATION_TOKEN_EXPIRATION/60_000)
        );
    }
}
