package org.example.portfoliospring1.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JwtAuthFilter doFilterInternal called");

        // jwt 검증
        // 1. 성공
        // SecurityContextHolder.getContext().setAuthentication(auth);
        // dofilter

        // 2. 실패(Throw Exception)

        filterChain.doFilter(request, response);
    }
}
