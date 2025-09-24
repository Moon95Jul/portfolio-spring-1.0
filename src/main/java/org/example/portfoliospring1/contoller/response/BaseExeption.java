package org.example.portfoliospring1.contoller.response;

import lombok.Getter;

@Getter
public class BaseExeption extends RuntimeException {
    public final BaseResponseStatusEnum statusEnum;

    public BaseExeption(BaseResponseStatusEnum baseResponseStatusEnum) {
        this.statusEnum = baseResponseStatusEnum;
    }
}
