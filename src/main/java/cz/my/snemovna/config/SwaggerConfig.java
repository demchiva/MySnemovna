package cz.my.snemovna.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
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
                        .url("https://www.psp.cz/sqw/hp.sqw"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .addServersItem(new Server().url("http://localhost:8085").description("Local ENV"))
                .addServersItem(new Server().url("https://my-snemovna.herokuapp.com").description("PROD ENV"));
    }

    /**
     * The SecurityScheme bean configuration.
     */
    @Bean
    public SecurityScheme securityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .name("bearerAuth")
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER);
    }
}
