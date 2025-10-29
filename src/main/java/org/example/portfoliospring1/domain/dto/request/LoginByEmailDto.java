package org.example.portfoliospring1.domain.dto.request;

import lombok.Data;

@Data
public class LoginByEmailDto {
    private String email;
    private String password;
}
