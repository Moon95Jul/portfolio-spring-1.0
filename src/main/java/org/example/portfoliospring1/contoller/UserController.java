package org.example.portfoliospring1.contoller;

import lombok.RequiredArgsConstructor;
import org.example.portfoliospring1.contoller.response.BaseResponse;
import org.example.portfoliospring1.domain.dto.UserDto;
import org.example.portfoliospring1.domain.dto.request.AddUserDto;
import org.example.portfoliospring1.service.UserService;
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
    public BaseResponse<Long> addUser(@RequestBody AddUserDto addUserDto) {
        return new BaseResponse<>(userService.addUser(addUserDto));
    }

    @GetMapping("/is-valid-nickname")
    public BaseResponse<Boolean> isValidNickname(@RequestParam String nickname) {
        return new BaseResponse<>(userService.isValidNickname(nickname));
    }

    @PostMapping("/login")
    public BaseResponse<String> login() {
        return new BaseResponse<>(userService.login("",""));
    }
}
