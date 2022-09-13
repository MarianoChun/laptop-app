package com.example.obejercicioSesion11y12.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiDetails())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();


    }

    private ApiInfo apiDetails(){
        return new ApiInfo("Spring Boot Laptop API REST",
                "Laptop Api rest DOCS",
                "1.0",
                "",
                new Contact("John Doe", "", "John@example.com"),
                "MIT",
                "",
                Collections.emptyList());
    }
}
