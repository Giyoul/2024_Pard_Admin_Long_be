package com.pard.admlong_be.global;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
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
                        .title("파드 지옥의 5차과제")
                        .description("설명")
                        .version("1.0.0"));
    }
}
