package com.basigo.project.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${base.url}")
    private String baseUrl;

    @Bean
    public OpenAPI customOpenAPI() {

        List<Server> servers = new ArrayList<>();

        if (activeProfile.equals("local")) {
            servers.add(new Server().url("http://localhost:8080"));
        } else {
            servers.add(new Server().url(baseUrl));
        }


        return new OpenAPI()
                .info(new Info()
                        .title("Mutitenancy")
                        .version("1.0")
                        .description("API documentation for the Mutitenancy API")
                        .contact(new Contact().name("Mutitenancy").email("info@company.com")
                        )).servers(servers);
    }
}

