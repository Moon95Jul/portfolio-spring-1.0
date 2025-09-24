package org.example.portfoliospring1.contoller.response;

import lombok.Getter;

@Getter
public enum BaseResponseStatusEnum {
    SUCCESS(20000, "요청에 성공 했습니다."),
    NOT_FOUND_DATA(40040, "데이터가 존재하지 않습니다."),
    DUPLICATED_NICKNAME(40041, "중복된 닉네임입니다."),
    DUPLICATED_EMAIL(40042, "중복된 이메일입니다.");

    private final int statusCode;
    private final String message;

    BaseResponseStatusEnum(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}
