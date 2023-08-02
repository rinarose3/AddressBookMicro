package git.rinarose3.abjava.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Address Book API")
                        .version("1.0.0")
                        .description("Его API предоставляет конечные точки для управления контактов в адресной книге. " +
                                     "Он позволяет получить список, добавить новый контакт, " +
                                     "обновить запись и удалить ее по идентификатору.")
                        .contact(new Contact()
                            .name("Рыжкова Ирина")
                            .email("Ryzhkova_IE@mail.ru")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Рабочий сервер")
                ));
    }
}
