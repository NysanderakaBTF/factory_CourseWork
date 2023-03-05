package com.fox.factory.entities.dto;

import com.fox.factory.entities.AttendanceTicket;
import com.fox.factory.entities.dto.UserInTicketDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the {@link AttendanceTicket} entity
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class AttendanceTicketDto implements Serializable {
    private final Long id;
    private final Date attandenceDate;
    private final Date creationDate;
    private final Double price;
    private final UserInTicketDto user;
}