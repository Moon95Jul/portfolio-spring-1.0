package org.example.portfoliospring1.contoller;

import lombok.RequiredArgsConstructor;
import org.example.portfoliospring1.config.auth.JwtUserPrincipal;
import org.example.portfoliospring1.contoller.response.BaseResponse;
import org.example.portfoliospring1.domain.dto.UserDto;
import org.example.portfoliospring1.domain.dto.request.AddUserDto;
import org.example.portfoliospring1.domain.dto.request.LoginByEmailDto;
import org.example.portfoliospring1.domain.dto.request.LoginByKakaoDto;
import org.example.portfoliospring1.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get-user")
    public BaseResponse<UserDto> getUser(@RequestParam String nickname) {
        return new BaseResponse<>(userService.getUser(nickname));
    }
    @GetMapping("/get-users")
    public BaseResponse<List<UserDto>> getUser() {
        return new BaseResponse<>(userService.getUsers());
    }

    @PostMapping("/add-user")
    public BaseResponse<String> addUser(@AuthenticationPrincipal JwtUserPrincipal principal,
                                      @RequestBody AddUserDto addUserDto) {


        return new BaseResponse<>(userService.addUser(addUserDto, principal.getProvider()));
    }

    @GetMapping("/public/is-valid-nickname")
    public BaseResponse<Boolean> isValidNickname(@RequestParam String nickname) {
        return new BaseResponse<>(userService.isValidNickname(nickname));
    }

    @PostMapping("/public/login-by-email")
    public BaseResponse<String> loginByEmail(@RequestBody LoginByEmailDto loginByEmailDto) {
        return new BaseResponse<>(userService.login(loginByEmailDto));
    }

    @PostMapping("/public/login-by-kakao")
    public BaseResponse<String> loginByKakao(@RequestBody LoginByKakaoDto loginByKakaoDto) {
        System.out.println("loginByKakaoDto = " + loginByKakaoDto);
        return new BaseResponse<>(userService.loginByKakao(loginByKakaoDto));
    }

    @PostMapping("/me")
    public BaseResponse<UserDto> me(@AuthenticationPrincipal JwtUserPrincipal principal) {
        System.out.println("principal.getUserId() : = " + principal.getUserId());

        if (principal.getUserId() == null) {
            return new BaseResponse<>(new UserDto());
        }

        return new BaseResponse<>(userService.me(principal.getUserId()));
    }

    // 스프링 시큐리티

}
