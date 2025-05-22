package com.skillstack.skilltracker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI myCustomConfig(){
        return new OpenAPI()
                .info(
                        new Info().title("Skill Tracker API")
                                .description("By Keshav Garg")
                )
                .servers(
                        List.of(new Server().url("http://localhost:8080").description("Local server"),
                                new Server().url("https://skilltracker.example.com").description("Production server")
                        )
                )
                .tags(
                        List.of(
                                new Tag().name("Skill").description("Skill management API"),
                                new Tag().name("Health Check").description("Health check API")
                        )
                );
    }
}
