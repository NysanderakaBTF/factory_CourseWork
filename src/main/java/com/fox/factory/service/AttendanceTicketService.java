package com.fox.factory.service;

import com.fox.factory.entities.AttendanceTicket;
import com.fox.factory.repositories.AttendanceTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AttendanceTicketService {
    @Autowired
    private AttendanceTicketRepository attendanceTicketRepository;

    public AttendanceTicket createAttendanceTicket(AttendanceTicket at){
        return attendanceTicketRepository.save(at);
    }
    public AttendanceTicket findById(Long id){
        return attendanceTicketRepository.findById(id).orElse(null);
    }
    public List<AttendanceTicket> findAllByDate(Date d){
        return attendanceTicketRepository.findAllByAttandenceDate(d);
    }
    public List<AttendanceTicket> findByDateBetween(Date d1, Date d2){
        return attendanceTicketRepository.findAllByAttandenceDateBetween(d1, d2);
    }
    public AttendanceTicket updateTicket(Long id, AttendanceTicket upInst){
        var a = attendanceTicketRepository.findById(id).orElse(null);
        if (a!=null){
            a.setPrice(upInst.getPrice());
            a.setUser(upInst.getUser());
            a.setAttandenceDate(upInst.getAttandenceDate());
            a.setCreationDate(upInst.getCreationDate());
            return attendanceTicketRepository.save(a);
        }
        return null;
    }

    public void delete(Long id){
        attendanceTicketRepository.deleteById(id);
    }
}
