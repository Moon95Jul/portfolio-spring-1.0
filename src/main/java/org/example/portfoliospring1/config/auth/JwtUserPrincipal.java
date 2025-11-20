package org.example.portfoliospring1.config.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtUserPrincipal {
    private final Long userId;
    private final String nickname;
    private final String email;
    private final String Provider;

}
