package cz.my.snemovna.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger documentation global config.
 */
@Configuration
public class SwaggerConfig {

    /**
     * The OpenApi bean configuration.
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MySnemovna API")
                        .description("MySnemovna API documentation")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .email("demchenko-vanek@seznam.cz")
                                .name("Ivan Demchenko"))
                        .license(new License().name("Ivan Demchenko")))
                .externalDocs(new ExternalDocumentation()
                        .description("Data source")
                        .url("https://www.psp.cz/sqw/hp.sqw")
                );
    }
}
