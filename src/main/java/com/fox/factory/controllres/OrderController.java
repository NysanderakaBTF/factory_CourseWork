package com.fox.factory.controllres;

import com.fox.factory.entities.dto.OrderDto;
import com.fox.factory.exceptions.InchangableOrderException;
import com.fox.factory.service.MyEmailService;
import com.fox.factory.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.event.ListDataEvent;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;
    private MyEmailService emailService;

    @Operation(summary = "Create an order")
    @PostMapping("/new")
    public ResponseEntity<OrderDto> create(@RequestBody OrderDto dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Delete order")
    @PostMapping("/{id}/delete")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "View an order")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOreder(@PathVariable Long id){
        return ResponseEntity.ok(service.getByIdDto(id));
    }

    @Operation(summary = "All users orders")
    @GetMapping("/")
    public ResponseEntity<List<OrderDto>>  allForUser(@RequestParam Long uid){
        return ResponseEntity.ok(service.getAllByUserId(uid));
    }

    @Operation(summary = "Set order status")
    @PutMapping("/{id}/change")
    public ResponseEntity<OrderDto> changeOrder(@PathVariable Long id, @RequestBody OrderDto dto) throws InchangableOrderException {
        var order = service.update(id, dto);
        emailService.sendEmail("Order :"+id+"status set to "+dto.getStatus(), dto.getUserInOrder().getEmail());
        return ResponseEntity.ok(order);
    }


}
