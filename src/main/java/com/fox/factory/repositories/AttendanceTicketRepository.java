package com.fox.factory.repositories;

import com.fox.factory.entities.AttendanceTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AttendanceTicketRepository extends JpaRepository<AttendanceTicket, Long> {
    List<AttendanceTicket> findAllByAttandenceDateBetween (Date d1, Date d2);
    List<AttendanceTicket> findAllByAttandenceDate (Date d);
}