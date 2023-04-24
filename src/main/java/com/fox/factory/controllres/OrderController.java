package com.fox.factory.controllres;

import com.fox.factory.entities.dto.OrderDto;
import com.fox.factory.exceptions.InchangableOrderException;
import com.fox.factory.service.MyEmailService;
import com.fox.factory.service.OrderService;
import com.fox.factory.service.mappers.OrderMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is a controller class that handles the requests that are sent to the server.
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;
    private final OrderMapper mapper;
    private MyEmailService emailService;

/**
 * Create an order
 * 
 * @param dto The object that will be created.
 * @return A ResponseEntity with an OrderDto
 */
    @Operation(summary = "Create an order")
    @PostMapping("/new")
    public ResponseEntity<OrderDto> create(@RequestBody OrderDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

/**
 * Delete order
 * 
 * @param id The id of the order to be deleted.
 * @return A ResponseEntity object is being returned.
 */
    @Operation(summary = "Delete order")
    @PostMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

/**
 * View an order
 * 
 * @param id The id of the order to be retrieved
 * @return A ResponseEntity with an OrderDto
 */
    @Operation(summary = "View an order")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOreder(@PathVariable Long id) {
        return ResponseEntity.ok(service.getByIdDto(id));
    }

/**
 * All users orders
 * 
 * @param uid The user id
 * @return A list of OrderDto objects
 */
    @Operation(summary = "All users orders")
    @GetMapping("/{uid}/user")
    public ResponseEntity<List<OrderDto>> allForUser(@RequestParam Long uid) {
        return ResponseEntity.ok(service.getAllByUserId(uid));
    }

/**
 * Set order status
 * 
 * @param id The id of the order to be changed
 * @param dto the object that will be used to update the order
 * @return ResponseEntity<OrderDto>
 */
    @Operation(summary = "Set order status")
    @PutMapping("/{id}/change")
    public ResponseEntity<OrderDto> changeOrder(@PathVariable Long id, @RequestBody OrderDto dto) throws InchangableOrderException {
        var order = service.update(id, dto);
        // Sending an email to the user with the order id and the status of the order.
        emailService.sendEmail("Order :" + id + "status set to " + dto.getStatus(), dto.getUserInOrder().getEmail());
        // Returning the order object.
        return ResponseEntity.ok(order);
    }


}
