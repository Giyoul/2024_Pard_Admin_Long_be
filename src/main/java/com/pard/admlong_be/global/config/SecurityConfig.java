package com.pard.admlong_be.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
//    private final PrincipalOauth2UserService principalOauth2UserService;
    private final CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable); // 세션 방식으로 구현하기 위해 csrf를 풀어주는 코드
        http.addFilter(corsConfig.corsFilter());
        http.authorizeHttpRequests( // http로 들어오는 request를 받아준다는 뜻
                au -> au.anyRequest().permitAll()
        );
//        http.oauth2Login(   // http로 들어오는 oauth2 login을 로그인 관련 처리를 어떻게 할 것인지에 대해 하는 것이다.
//                oauth -> oauth
//                        .loginPage("/loginForm")
//                        .defaultSuccessUrl("/loginForm")
//                        .userInfoEndpoint(
//                                userInfoEndpointConfig ->
//                                        userInfoEndpointConfig.userService(principalOauth2UserService)
//                        )
//        );
        return http.build();
    }
}
