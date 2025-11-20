package org.example.portfoliospring1.service;

import lombok.RequiredArgsConstructor;
import org.example.portfoliospring1.contoller.response.BaseException;
import org.example.portfoliospring1.contoller.response.BaseResponseStatusEnum;
import org.example.portfoliospring1.domain.dto.UserDto;
import org.example.portfoliospring1.domain.dto.infra.KapiUserMeDto;
import org.example.portfoliospring1.domain.dto.infra.KauthTokenDto;
import org.example.portfoliospring1.domain.dto.request.AddUserDto;
import org.example.portfoliospring1.domain.dto.request.LoginByEmailDto;
import org.example.portfoliospring1.domain.dto.request.LoginByKakaoDto;
import org.example.portfoliospring1.domain.entity.User;
import org.example.portfoliospring1.infra.feign.KapiFeignClient;
import org.example.portfoliospring1.infra.feign.KauthFeignClient;
import org.example.portfoliospring1.repository.UserRepository;
import org.example.portfoliospring1.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    @Value("${kakao.api}")
    private String KAKAKO_API_KEY;
    @Value("${kakao.secret}")
    private String KAKAKO_SECRET_KEY;


    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final KauthFeignClient kauthFeignClient;
    private final KapiFeignClient kapiFeignClient;


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

    public String addUser(AddUserDto addUserDto, String providerId) {
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
        System.out.println("user.setProviderId(providerId);" + providerId);
        user.setProviderId(providerId);

        userRepository.save(user);
        return jwtUtil.generateToken(user.getId(), user.getEmail(), user.getEmail(), user.getProviderId());
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
            return jwtUtil.generateToken(user.getId(), user.getNickname(), user.getEmail(), user.getProviderId());

        } catch (Exception e) {
            throw  new BaseException(BaseResponseStatusEnum.FAILED_TO_LOGIN);
        }


    }

    public String loginByKakao(LoginByKakaoDto loginByKakaoDto) {
        System.out.println(loginByKakaoDto.getCode() + " @code");

        try {
            KauthTokenDto kauthTokenDto = kauthFeignClient.getKakaoToken(
                    "authorization_code",
                    KAKAKO_API_KEY,
                    "http://localhost:3000/login/kakao",
                    loginByKakaoDto.getCode(),
                    KAKAKO_SECRET_KEY
            );



            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + kauthTokenDto.access_token());

            KapiUserMeDto kapiUserMeDto = kapiFeignClient.userMe(headers);

            // 카카오 ID 까지는 찾음

            // 카카오 ID로 우리 DB에 유저가 있는지 확인
            Optional<User> userOptional = userRepository.findByProviderIdAndDeleted(kapiUserMeDto.id(), false);

            if(userOptional.isPresent()) {
                User user = userOptional.get();
                // 있으면 로그인 시킴
                return jwtUtil.generateToken(user.getId(), user.getNickname(), user.getEmail(), user.getProviderId());
            }

            // 2. 없으면 회원가입
            // 아래 두 정보가 클라이언트에게 필요함. 우리는 두 저옵를 jwt 토큰으로 클라이언트에게 전달할거임.
            // 회원가입이 필요하다 => 토큰의 userId가 null 이면 회원가입이 필요.
            // provider id
            return jwtUtil.generateToken(null,null,null, kapiUserMeDto.id());

        } catch (Exception e) {
            e.printStackTrace();
            throw  new BaseException(BaseResponseStatusEnum.FAILED_TO_LOGIN);
        }
    }

    public UserDto me(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            return new UserDto(optionalUser.get());
        }

        return null;
    }

}
