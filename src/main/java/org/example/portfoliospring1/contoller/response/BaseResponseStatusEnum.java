package org.example.portfoliospring1.contoller.response;

import lombok.Getter;

@Getter
public enum BaseResponseStatusEnum {
    SUCCESS(20000, "요청에 성공했습니다."),
    NOT_FOUND_DATA(40040, "데이터가 존재하지 않습니다."),

    FAILED_TO_LOGIN(40040, "로그인에 실패했습니다."),
    DUPLICATED_NICKNAME(40040, "중복된 닉네임입니다."),
    DUPLICATED_EMAIL(40040, "이미 존재하는 e-mail입니다."),
    INVALID_NICKNAME_LENGTH(40040, "닉네임은 3글자 이상입니다."),
    INVALID_NICKNAME_WORD(40040, "시용할 수 없는 딘어가 포함되어 있습니다.");

    private final int statusCode;
    private final String message;

    BaseResponseStatusEnum(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}
