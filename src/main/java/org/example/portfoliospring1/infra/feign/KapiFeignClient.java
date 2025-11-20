package org.example.portfoliospring1.infra.feign;

import org.example.portfoliospring1.domain.dto.infra.KapiUserMeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.Map;

@FeignClient(name="kapi", url="https://kapi.kakao.com")
public interface KapiFeignClient {

    @GetMapping("v2/user/me")
    KapiUserMeDto userMe(@RequestHeader Map<String, String> headers);


}
