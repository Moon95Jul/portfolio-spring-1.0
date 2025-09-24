package org.example.portfoliospring1.contoller;

import org.example.portfoliospring1.contoller.response.BaseExeption;
import org.example.portfoliospring1.contoller.response.BaseResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(value = 1)
public class ExceptionController {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BaseExeption.class)
    @ResponseBody
    public BaseResponse<?> invaildRequestHandler(BaseExeption exception) {
        return new BaseResponse<>(exception);
    }
}
