package com.pard.admlong_be.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

//    @Value("${recordon.server.domain}")
//    private String domain;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
//                .addServersItem(new Server().url(domain))
                .info(new Info()
                        .title("프로젝트 이름")
                        .description("설명")
                        .version("0.9.0"));
    }
}
