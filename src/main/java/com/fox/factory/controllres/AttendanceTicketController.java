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


/**
 * This class is used to handle the requests that are coming from the client side.
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ticket")
public class AttendanceTicketController {
// The above code is a controller class that is used to handle the requests that are coming from the
// client side.
    private final AttendanceTicketService service;
    private final MyEmailService emailService;
    //This API is used to get all the details of the ticket by its Id.
    /**
     * Get all info of ticket by id
     * 
     * @param id The id of the ticket
     * @return The response entity with the status code of 200 and the body of the response is the
     * service.findById(id)
     */
    @Operation(summary = "Get all info of ticket by id")
    @GetMapping("/{id}")
    public ResponseEntity<AttendanceTicketDto> ticketDetail(@PathVariable Long id) {
        // Returning the response entity with the status code of 200 and the body of the response is
        // the service.findById(id)
        return ResponseEntity.ok(service.findById(id));
    }

   /**
    * Get all users tickets
    * 
    * @param user_id the id of the user
    * @return A list of AttendanceTicketDto objects
    */
    @Operation(summary = "Get all users tickets")
    @GetMapping("/u/{user_id}")
    public ResponseEntity<List<AttendanceTicketDto>> allTicketsByUser(@PathVariable Long user_id) {
        return ResponseEntity.ok(service.findByUserId(user_id));
    }

    /**
     * Find all given tickets for passed date
     * 
     * @param date 2019-01-01
     * @return A list of AttendanceTicketDto objects.
     */
    @Operation(summary = "find all given tickets for passed date")
    @GetMapping("/find_by_date")
    public ResponseEntity<List<AttendanceTicketDto>> findTicketsByDate(@RequestParam LocalDate date) {
        return ResponseEntity.ok(
                service.findAllByDate(date)
        );
    }

   /**
    * Find all given tickets between given dates
    * 
    * @param from 2020-01-01
    * @param to 2020-01-01
    * @return A list of AttendanceTicketDto objects.
    */
    @Operation(summary = "find all given tickets between given dates")
    @GetMapping("/find_by_date_between")
    public ResponseEntity<List<AttendanceTicketDto>> findTicketsByDatesBetween(@RequestParam LocalDate from,
                                                                               @RequestParam LocalDate to) {
        return ResponseEntity.ok(
                service.findByDateBetween(from, to)
        );
    }

    /**
     * Create a new ticket based on dto
     * 
     * @param ticketDto the object that is sent to the server
     * @return A ResponseEntity with a body of AttendanceTicketDto
     */
    @Operation(summary = "Creeate a new ticket based on dto")
    @PostMapping("/new")
    public ResponseEntity<AttendanceTicketDto> createTicket(@RequestBody AttendanceTicketDto ticketDto){
        emailService.sendEmail("Your ticket"+ticketDto+"is ready", ticketDto.getUser().getEmail());
        return ResponseEntity.ok(service.createAttendanceTicket(ticketDto));
    }

/**
 * Delete tickeet buy id
 * 
 * @param id The id of the ticket to be deleted.
 * @return A ResponseEntity object is being returned.
 */
    @Operation(summary = "delete tickeet buy id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

/**
 * Update info on existing ticket
 * 
 * @param id the id of the ticket to be updated
 * @param dto AttendanceTicketDto
 * @return A ResponseEntity with a status code of 200 and the updated AttendanceTicketDto.
 */
    @Operation(summary = "update info on existing ticket")
    @PutMapping("/{id}")
    public ResponseEntity<AttendanceTicketDto> update(@PathVariable Long id, @RequestBody AttendanceTicketDto dto){
        return ResponseEntity.ok(
                service.updateTicket(id, dto)
        );
    }

}
