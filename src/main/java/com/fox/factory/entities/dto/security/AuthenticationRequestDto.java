package com.fox.factory.entities.dto.security;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder

/**
 * A class that represents a request for authentication/request for a token.
 */
@Schema(description = "Запрос аутентификации/запрос для получения токена")
public class AuthenticationRequestDto {
// A validation annotation.
    @Schema(description = "Имя пользователя")
    @NotBlank(message = "Необходимо указать логин/имя пользователя")
    private String username;

// A validation annotation.
    @Schema(description = "Пароль")
    @NotBlank(message = "Необходимо указать пароль")
    private String password;
}
