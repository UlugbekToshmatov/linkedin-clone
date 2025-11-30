package com.linkedin.linkedin.models.account_verification;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountVerification {
    private Long id;
    private Long userId;
    private String url;
}
