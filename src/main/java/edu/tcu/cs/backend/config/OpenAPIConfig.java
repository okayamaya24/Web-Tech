package edu.tcu.cs.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TCU Sports Management API")
                        .description("RESTful API for managing sports games, schedules, crew assignments, and availability")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("TCU CS")
                                .email("contact@tcu.edu")
                                .url("https://www.tcu.edu/cs"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Development Server"),
                        new Server().url("https://api.tcu-sports.example.com").description("Production Server")
                ));
    }
} 