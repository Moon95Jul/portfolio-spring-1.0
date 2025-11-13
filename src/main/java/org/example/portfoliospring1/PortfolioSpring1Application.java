package org.example.portfoliospring1;

import org.example.portfoliospring1.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients(basePackages ="com.example.portfoliospring1")
public class PortfolioSpring1Application {

    public static void main(String[] args) {
        SpringApplication.run(PortfolioSpring1Application.class, args);
    }

}
