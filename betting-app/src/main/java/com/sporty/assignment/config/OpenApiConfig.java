package com.sporty.assignment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sports Betting Settlement Trigger Service")
                        .description(
                                "Backend service simulating sports betting event outcome handling and bet settlement via Kafka and RocketMQ.")
                        .version("1.0"));
    }
}
