package com.fox.factory.entities.dto;

import com.fox.factory.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class UserInOrderDto implements Serializable {
    private final Long id;
    @Size(min = 5)
    @NotBlank
    private final String username;
    private final String firstName;
    private final String address;
    @Email
    private final String email;
    private final String lastName;
}