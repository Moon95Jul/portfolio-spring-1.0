package org.example.portfoliospring1.service;

import lombok.RequiredArgsConstructor;
import org.example.portfoliospring1.contoller.response.BaseException;
import org.example.portfoliospring1.contoller.response.BaseResponseStatusEnum;
import org.example.portfoliospring1.domain.dto.UserDto;
import org.example.portfoliospring1.domain.dto.request.AddUserDto;
import org.example.portfoliospring1.domain.dto.request.LoginByEmailDto;
import org.example.portfoliospring1.domain.entity.User;
import org.example.portfoliospring1.repository.UserRepository;
import org.example.portfoliospring1.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserDto getUser(String nickname) {
        User user = userRepository.findByNickname(nickname);
        if (user == null) {
            return null;
        }

        return new UserDto(user);
    }

    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> new UserDto(user)).collect(Collectors.toList());
//        return users.stream().map(UserDto::new).collect(Collectors.toList());
    }

    public Long addUser(AddUserDto addUserDto) {
        if (!userRepository.findAllByNickname(addUserDto.getNickname()).isEmpty()) {
            throw new BaseException(BaseResponseStatusEnum.DUPLICATED_NICKNAME);
        }

        if (!userRepository.findAllByEmail(addUserDto.getEmail()).isEmpty()) {
            throw new BaseException(BaseResponseStatusEnum.DUPLICATED_EMAIL);
        }

        User user = new User();
        user.setNickname(addUserDto.getNickname());
        user.setEmail(addUserDto.getEmail());
        user.setPassword(addUserDto.getPassword());

        userRepository.save(user);
        return user.getId();
    }

    public Boolean isValidNickname(String nickname) {
        // 1. 문자열 길이 3글자 이상 체크
        if (nickname == null || nickname.length() < 3) {
            throw new BaseException(BaseResponseStatusEnum.INVALID_NICKNAME_LENGTH);
        }

        // 2. 사용할 수 없는 문자 체크 '바보', '멍청이' 단어 들어가면 불가
        if (nickname.contains("바보") || nickname.contains("멍청이")) {
            throw new BaseException(BaseResponseStatusEnum.INVALID_NICKNAME_WORD);
        }

        // 3. 중복되었는지 확인
        if (!userRepository.findAllByNickname(nickname).isEmpty()) {
            throw new BaseException(BaseResponseStatusEnum.DUPLICATED_NICKNAME);
        }

        // 4. 위에 해당 안 하면 true
        return true;
    }

    public String login(LoginByEmailDto loginByEmailDto) {
        try {
            User user = userRepository.findByEmailAndPassword(loginByEmailDto.getEmail(), loginByEmailDto.getPassword())
                    .orElseThrow();
            return jwtUtil.generateToken(user.getId(), user.getNickname(), user.getEmail());

        } catch (Exception e) {
            throw  new BaseException(BaseResponseStatusEnum.FAILED_TO_LOGIN);
        }


    }

}
