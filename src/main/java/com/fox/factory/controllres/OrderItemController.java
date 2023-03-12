package com.fox.factory.controllres;

import com.fox.factory.entities.dto.OrderDto;
import com.fox.factory.entities.dto.OrderItemInListDto;
import com.fox.factory.repositories.OrderRepository;
import com.fox.factory.service.OrderItemService;
import com.fox.factory.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders/{oid}/items")
public class OrderItemController {
    private final OrderItemService service;
    private final OrderService orderService;

    @Operation(summary = "creates order item and adds it to order")
    @PostMapping("/add")
    public ResponseEntity<OrderDto> addItem(@PathVariable Long oid, @RequestBody OrderItemInListDto item){
        return ResponseEntity.ok(orderService.addOrderItem(oid, service.getObjectFromDto(service.create(item))));
    }

    @Operation(summary = "Delete item in order")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<OrderDto> deleteItem(@PathVariable Long oid, @PathVariable Long id){
        orderService.removeOrderItem(oid, id);
        service.delete(id);
        return ResponseEntity.ok(orderService.getByIdDto(oid));
    }

    @Operation(summary = "change quantity of item")
    @PutMapping("/{id}/update")
    public ResponseEntity<OrderItemInListDto> update(@PathVariable Long oid, @PathVariable Long id, @RequestBody OrderItemInListDto item){
        if(item.getQuantity()<0){
            throw new InvalidDataAccessApiUsageException("quantity must be not negative");
        }
        return ResponseEntity.ok(service.update(id, item));
    }

}
