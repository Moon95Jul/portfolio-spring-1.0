package org.example.portfoliospring1.infra.feign;

import org.example.portfoliospring1.domain.dto.infra.KauthTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="kauth", url="https://kauth.kakao.com")
public interface KauthFeignClient {
    @PostMapping("/oauth/token")
    KauthTokenDto getKakaoToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code") String code,
            @RequestParam("client_secret") String clientSecret
    );


}
