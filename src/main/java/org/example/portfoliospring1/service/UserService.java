package org.example.portfoliospring1.service;

import lombok.RequiredArgsConstructor;
import org.example.portfoliospring1.contoller.response.BaseExeption;
import org.example.portfoliospring1.contoller.response.BaseResponseStatusEnum;
import org.example.portfoliospring1.domain.dto.UserDto;
import org.example.portfoliospring1.domain.dto.request.AddUserDto;
import org.example.portfoliospring1.domain.entity.User;
import org.example.portfoliospring1.repository.UserRepository;
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

    public UserDto getUser(String nickname) {
        User user = userRepository.findByNickname(nickname);
        if (user == null) {
            return null;
        }
        return new UserDto(user);
    }


    public List<UserDto> getUser() {
        List<User> users = userRepository.findAll();

//      return users.stream().map(user -> new UserDto(user)).collect(Collectors.toList());
        return users.stream().map(UserDto::new).collect(Collectors.toList());
    }


    public Long addUser(AddUserDto addUserDto) {
        // 회원가입
        // 1. 중복된 닉네임이면 가입 못하게
        if (!userRepository.findAllByNickname(addUserDto.getNickname()).isEmpty()) {
            throw new BaseExeption(BaseResponseStatusEnum.DUPLICATED_NICKNAME );
        }
        // 2. 중복된 이메일이면 가입 못하게
        // if (userRepository.findAllByEmail(addUserDto.getEmail()).size() != 0)
        if (!userRepository.findAllByEmail(addUserDto.getEmail()).isEmpty()) {
            throw new BaseExeption(BaseResponseStatusEnum.DUPLICATED_EMAIL );
        }

        User user = new User();
        user.setNickname(addUserDto.getNickname());
        user.setEmail(addUserDto.getEmail());
        user.setPassword(addUserDto.getPassword());

        userRepository.save(user);
        return user.getId();
    }
}
