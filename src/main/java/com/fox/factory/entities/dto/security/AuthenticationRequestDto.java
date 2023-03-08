package com.fox.factory.entities.dto.security;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder

@Schema(description = "Запрос аутентификации/запрос для получения токена")
public class AuthenticationRequestDto {
    @Schema(description = "Имя пользователя")
    @NotBlank(message = "Необходимо указать логин/имя пользователя")
    private String username;

    @Schema(description = "Пароль")
    @NotBlank(message = "Необходимо указать пароль")
    private String password;
}
