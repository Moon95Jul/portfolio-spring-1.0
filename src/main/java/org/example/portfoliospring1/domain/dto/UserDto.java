package org.example.portfoliospring1.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.portfoliospring1.domain.entity.User;

@Data
@NoArgsConstructor
public class UserDto {
    private String nickname;
    private String email;

    public UserDto(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }

    public UserDto(User user) {
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }
}
