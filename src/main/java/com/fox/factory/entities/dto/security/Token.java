package com.fox.factory.entities.dto.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * A class that represents a bearer token.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Bearer Токен")
public class Token {

    @Schema(description = "Значение Bearer токена")
    private String tokenValue;

}
