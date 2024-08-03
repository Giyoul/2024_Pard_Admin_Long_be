package com.pard.admlong_be.global.config;

import com.pard.admlong_be.global.responses.error.handler.AccessDeniedHandlerImpl;
import com.pard.admlong_be.global.responses.error.handler.JwtAuthenticationEntryPoint;
import com.pard.admlong_be.global.security.cookie.service.CookieService;
import com.pard.admlong_be.global.security.jwt.JWTFilter;
import com.pard.admlong_be.global.security.jwt.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//    private final JWTUtil jwtUtil;
//    private final AccessDeniedHandlerImpl accessDeniedHandler;
//    private final CookieService cookieService;
//    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//
////    @Bean
////    RoleHierarchy roleHierarchy() {
////        RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
////        roleHierarchyImpl.setHierarchy("ROLE_ADMIN > ROLE_OB > ROLE_YB");
////        return roleHierarchyImpl;
////    }
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf((auth) -> auth.disable());
//        http
//                .formLogin(auth -> auth.disable());
//        http
//                .httpBasic(auth -> auth.disable());
//
//        http
//                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
//                    @Override
//                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//                        return null;
//                    }
//                    @Bean
//                    public CorsConfigurationSource configurationSource() {
//                        CorsConfiguration corsConfiguration = new CorsConfiguration();
//                        corsConfiguration.addAllowedOrigin("http://localhost:3000");
//                        corsConfiguration.addAllowedMethod("*");
//                        corsConfiguration.addAllowedHeader("*");
//                        corsConfiguration.setAllowCredentials(true);
//                        corsConfiguration.setMaxAge(3600L); //preflight 결과를 1시간동안 캐시에 저장
//                        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//                        source.registerCorsConfiguration("/**", corsConfiguration);
//                        return source;
//                    }
//                }));
//
//        http
//                .addFilterBefore(new JWTFilter(jwtUtil, cookieService), UsernamePasswordAuthenticationFilter.class);
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/test","/swagger-ui/**","/v3/api-docs/**","/api/login", "/api/register","/error", "/api/health").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/**").permitAll()
//                        .requestMatchers(HttpMethod.DELETE, "/api/**").permitAll()
//                        .requestMatchers(HttpMethod.PATCH, "/api/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
//                        .requestMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
////                        .requestMatchers("/v1/**").hasRole("YB")
////                        .requestMatchers("/hi/hi").hasRole("OB")
////                        .requestMatchers("/hi/hello").hasRole("YB")
//                        .anyRequest().authenticated()
//                );
//        http
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        http
//                .exceptionHandling(exception -> exception
//                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                        .accessDeniedHandler(accessDeniedHandler));
//
//
//        return http.build();
//    }
//}
//
//
//
////import lombok.RequiredArgsConstructor;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
////import org.springframework.security.web.SecurityFilterChain;
////
////@Configuration
////@EnableWebSecurity
////@RequiredArgsConstructor
////public class SecurityConfig {
//////    private final PrincipalOauth2UserService principalOauth2UserService;
////    private final CorsConfig corsConfig;
////
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        http.csrf(AbstractHttpConfigurer::disable); // 세션 방식으로 구현하기 위해 csrf를 풀어주는 코드
////        http.addFilter(corsConfig.corsFilter());
////        http.authorizeHttpRequests( // http로 들어오는 request를 받아준다는 뜻
////                au -> au.anyRequest().permitAll()
////        );
//////        http.oauth2Login(   // http로 들어오는 oauth2 login을 로그인 관련 처리를 어떻게 할 것인지에 대해 하는 것이다.
//////                oauth -> oauth
//////                        .loginPage("/loginForm")
//////                        .defaultSuccessUrl("/loginForm")
//////                        .userInfoEndpoint(
//////                                userInfoEndpointConfig ->
//////                                        userInfoEndpointConfig.userService(principalOauth2UserService)
//////                        )
//////        );
////        return http.build();
////    }
////}

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTUtil jwtUtil;
    private final AccessDeniedHandlerImpl accessDeniedHandler;
    private final CookieService cookieService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Value("${manageLongkerthon.server.domain}")
    private String serverDomain;

    @Value("${manageLongkerthon.client.domain1}")
    private String clientDomain1;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .formLogin((form) -> form.disable())
                .httpBasic((basic) -> basic.disable())
                .cors((cors) -> cors.configurationSource(corsConfigurationSource())) // Single configuration source
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((exceptions) ->
                        exceptions
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler)
                )
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/test", "/swagger-ui/**", "/v3/api-docs/**", "/api/login", "/api/register", "/error", "/api/health").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(new JWTFilter(jwtUtil, cookieService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000"); // 허용할 도메인 추가
        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedOrigin("http://localhost:3001");
        configuration.addAllowedOrigin(serverDomain); // 환경 변수로 받은 도메인 추가
        configuration.addAllowedOrigin(clientDomain1); // 환경 변수로 받은 도메인 추가
        configuration.addAllowedMethod("*"); // 모든 메서드 허용
        configuration.addAllowedHeader("*"); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 자격 증명 허용
        configuration.setMaxAge(3600L); // 사전 요청 결과를 1시간 동안 캐시
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
