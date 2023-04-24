package com.fox.factory.entities.dto;

import com.fox.factory.entities.AttendanceTicket;
import com.fox.factory.entities.dto.UserInTicketDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * A DTO for the {@link AttendanceTicket} entity
 * This class is a DTO for the AttendanceTicket entity
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class AttendanceTicketDto implements Serializable {
    private final Long id;
    private final LocalDate attandenceDate;
    private final LocalDate creationDate;
    private final Double price;
    private final UserInTicketDto user;

    @Override
    public String toString() {
        return "AttendanceTicketDto{" +
                "id=" + id +
                ", attandenceDate=" + attandenceDate +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", user=" + user +
                '}';
    }
}