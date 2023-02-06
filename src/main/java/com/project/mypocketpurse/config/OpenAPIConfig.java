package com.project.mypocketpurse.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myPocketPurseProject() {
        return new OpenAPI()
                .info(new Info()
                        .title("My Pocket Purse")
                        .description("Сервис, позволяющий следить за расходами и доходами")
                        .version("v0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .contact(new Contact().name("Dmitry O. Shiryaev")
                                .email("crystler93@gmail.com")
                                .url(""))
                );
    }
}
