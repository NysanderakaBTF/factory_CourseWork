package com.fox.factory.entities.dto;

import com.fox.factory.entities.User;
import jakarta.validation.constraints.Email;
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
public class UserInTicketDto implements Serializable {
    private final Long id;
    private final String firstName;
    @Email
    private final String email;
    private final String lastName;
}