package ca.montreal.mesmorize.configuration;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration

public class OpenAPIConfig {

@Bean
public OpenAPI springShopOpenAPI() {
    return new OpenAPI()
            .info(new Info().title("Mesmorize API")
                    .description("A Mermerizing way to Memorize!")
                    .version("v0.0.1")
                    .license(new License().name("Apache 2.0").url("http://springdoc.org")))
            .externalDocs(new ExternalDocumentation()
                    .description("Mesmorize Wiki Documentation")
                    .url("https://github.com/sjavaheri/Mesmorize/wiki"))
            .components(new Components()
                    .addSecuritySchemes("bearerAuth", new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT"))
                    .addSecuritySchemes("basicAuth", new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("basic")))
            .security(Arrays.asList(
                    new SecurityRequirement().addList("bearerAuth"),
                    new SecurityRequirement().addList("basicAuth")));
}

}
