package com.fox.factory.controllres;

import com.fox.factory.entities.dto.AttendanceTicketDto;
import com.fox.factory.service.AttendanceTicketService;
import com.fox.factory.service.MyEmailService;
import com.fox.factory.service.mappers.AttendanceTicketMapper;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ticket")
public class AttendanceTicketController {
    private final AttendanceTicketMapper mapper;
    private final AttendanceTicketService service;
    private final MyEmailService emailService;

    @Operation(summary = "Get all info of ticket by id")
    @GetMapping("/{id}")
    public ResponseEntity<AttendanceTicketDto> ticketDetail(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Get all users tickets")
    @GetMapping("/{user_id}")
    public ResponseEntity<List<AttendanceTicketDto>> allTicketsByUser(@PathVariable Long user_id) {
        return ResponseEntity.ok(service.findByUserId(user_id));
    }

    @Operation(summary = "find all given tickets for passed date")
    @GetMapping("/find_by_date")
    public ResponseEntity<List<AttendanceTicketDto>> findTicketsByDate(@RequestParam LocalDate date) {
        return ResponseEntity.ok(
                service.findAllByDate(date)
        );
    }

    @Operation(summary = "find all given tickets between given dates")
    @GetMapping("/find_by_date_between")
    public ResponseEntity<List<AttendanceTicketDto>> findTicketsByDatesBetween(@RequestParam LocalDate from,
                                                                               @RequestParam LocalDate to) {
        return ResponseEntity.ok(
                service.findByDateBetween(from, to)
        );
    }

    @Operation(summary = "Creeate a new ticket based on dto")
    @PostMapping("/new")
    public ResponseEntity<AttendanceTicketDto> createTicket(@RequestBody AttendanceTicketDto ticketDto){
        emailService.sendEmail("Your ticket"+ticketDto+"is ready", ticketDto.getUser().getEmail());
        return ResponseEntity.ok(service.createAttendanceTicket(ticketDto));
    }

    @Operation(summary = "delete tickeet buy id")
    @DeleteMapping("/{id}/deelte")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "update info on existing ticket")
    @PutMapping("/{id}/update")
    public ResponseEntity<AttendanceTicketDto> update(@PathVariable Long id, @RequestBody AttendanceTicketDto dto){
        return ResponseEntity.ok(
                service.updateTicket(id, dto)
        );
    }

}
