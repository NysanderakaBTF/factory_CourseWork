package com.fox.factory.repositories;

import com.fox.factory.entities.AttendanceTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

// A repository for the AttendanceTicket entity.
public interface AttendanceTicketRepository extends JpaRepository<AttendanceTicket, Long> {
    List<AttendanceTicket> findAllByAttandenceDateBetween (LocalDate d1, LocalDate d2);
    List<AttendanceTicket> findAllByAttandenceDate (LocalDate d);

    List<AttendanceTicket> findAllByUser_Id(Long id);
}