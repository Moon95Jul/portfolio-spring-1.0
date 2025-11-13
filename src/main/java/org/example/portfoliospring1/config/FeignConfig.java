package org.example.portfoliospring1.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "org.example.portfoliospring1")
public class FeignConfig {

}
