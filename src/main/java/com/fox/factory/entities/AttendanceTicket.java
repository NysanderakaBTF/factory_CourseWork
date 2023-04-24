package com.fox.factory.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

/**
 * AttendanceTicket has a many-to-one relationship with User.
 */
@Entity
@Getter
@Setter
public class AttendanceTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private LocalDate attandenceDate;
    private LocalDate creationDate;
    private Double price;

    @ManyToOne
    private User user;

}
