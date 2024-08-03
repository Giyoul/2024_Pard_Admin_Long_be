//package com.pard.admlong_be.global.config;
//
//import org.springframework.beans.factory.annotation.Value;
////import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
////import org.springframework.web.cors.CorsConfiguration;
////import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
////import org.springframework.web.filter.CorsFilter;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//    @Value("${manageLongkerthon.server.domain}")
//    private String domain;
//
//    @Value("${manageLongkerthon.client.domain1}")
//    private String domain1;
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:8080", "http://localhost:3000","http://localhost:3001", domain, domain1)
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }
////
////    @Bean
////    public CorsFilter corsFilter() {
////        UrlBasedCorsConfigurationSource source =
////                new UrlBasedCorsConfigurationSource();
////        CorsConfiguration config = new CorsConfiguration();
////
////        config.setAllowCredentials(true); //frontEnd에서 axios로 처리 가능하게 만들겠다
////        config.addAllowedOrigin("*"); //모든 ip에 응답을 허용하겠다
////        config.addAllowedHeader("*"); //모든 header에 응답을 허용하겠다
////        config.addAllowedMethod("*"); //모든 post,get,put,delete,patch 요청을 허용하겠다
////        source.registerCorsConfiguration("*", config); //api로 들어오는 모든 요청은 이 config를 따르겠다
////        return new CorsFilter(source);
////    }
//}