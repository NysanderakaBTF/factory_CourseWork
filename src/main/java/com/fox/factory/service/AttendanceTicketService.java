package com.fox.factory.service;

import com.fox.factory.entities.AttendanceTicket;
import com.fox.factory.entities.dto.AttendanceTicketDto;
import com.fox.factory.repositories.AttendanceTicketRepository;
import com.fox.factory.service.mappers.AttendanceTicketMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class AttendanceTicketService {
    private final AttendanceTicketRepository attendanceTicketRepository;
    private final AttendanceTicketMapper mapper;
    @Transactional
    public AttendanceTicketDto createAttendanceTicket(AttendanceTicketDto ticketDto){
        return mapper.toDto(attendanceTicketRepository.save(
                mapper.toEntity(ticketDto)
        ));
    }

    public AttendanceTicketDto findById(Long id){
        return mapper.toDto(attendanceTicketRepository.findById(id).orElse(null));
    }
    @Transactional
    public List<AttendanceTicketDto> findAllByDate(LocalDate date){
        return attendanceTicketRepository.findAllByAttandenceDate(date).stream().map(mapper::toDto).collect(Collectors.toList());
    }
    @Transactional
    public List<AttendanceTicketDto> findByDateBetween(LocalDate fromDate, LocalDate toDate){
        return attendanceTicketRepository.findAllByAttandenceDateBetween(fromDate, toDate).stream().map(mapper::toDto).collect(Collectors.toList());
    }
    @Transactional
    public AttendanceTicketDto updateTicket(Long id, AttendanceTicketDto upInst){
        return attendanceTicketRepository.findById(id)
                .map(attendanceTicket -> mapper.partialUpdate(upInst, attendanceTicket))
                .map(attendanceTicketRepository::save)
                .map(mapper::toDto)
                .orElse(null);
//        var ticket = attendanceTicketRepository.findById(id).orElse(null);
//        if (ticket==null)
//            return null;
//
//        return mapper.toDto(attendanceTicketRepository.save(
//                mapper.partialUpdate(upInst, ticket)
//        ));

    }
    @Transactional
    public void delete(Long id){
        attendanceTicketRepository.deleteById(id);
    }
    @Transactional
    public List<AttendanceTicketDto> findByUserId(Long uid){
        return attendanceTicketRepository.findAllByUser_Id(uid).stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
