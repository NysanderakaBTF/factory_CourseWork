package com.fox.factory.entities.dto;

import com.fox.factory.security.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * A DTO for the {@link com.fox.factory.entities.User} entity
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class UserInCommentDto implements Serializable {
    private final Long id;
    @Size(min = 5)
    @NotBlank
    private final String username;
    private final Role role;
}