package cat.itacademy.s05.t01.n01.S05T01N01.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI blackjackOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Blackjack API")
                        .description("API REST para gestionar jugadores y partidas del juego Blackjack")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Luis Ricardo Gutierrez Soliz")
                                .email("luriguso@gmail.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositorio GitHub del Proyecto")
                        .url("https://github.com/luriguso/S05T01N01"));
    }
}
