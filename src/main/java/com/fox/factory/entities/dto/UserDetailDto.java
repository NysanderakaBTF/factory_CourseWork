package com.fox.factory.entities.dto;

import com.fox.factory.entities.User;
import com.fox.factory.entities.dto.AttendanceTicketDto;
import com.fox.factory.entities.dto.CommentsDto;
import com.fox.factory.entities.dto.OrderDto;
import com.fox.factory.security.Role;
import com.fox.factory.security.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link User} entity
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class UserDetailDto implements Serializable {
    private final Long id;
    private final Set<CommentsDto> usersComments;
    private final Set<OrderDto> orders;
    private final Set<AttendanceTicketDto> tickets;
    @Size(min = 5)
    @NotBlank
    private final String username;
    private final String firstName;
    private final String address;
    @Email
    private final String email;
    @NotBlank
    private final String password;
    private final Role role;
    private final Status status;
    private final String lastName;
}