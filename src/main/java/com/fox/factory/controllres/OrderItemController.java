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

/**
 * It's a controller class that handles the requests for the order items.
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders/{oid}/items")
public class OrderItemController {
    private final OrderItemService service;
    private final OrderService orderService;

/**
 * Creates order item and adds it to order
 * 
 * @param oid order id
 * @param item OrderItemInListDto
 * @return ResponseEntity<OrderDto>
 */
    @Operation(summary = "creates order item and adds it to order")
    @PostMapping("/add")
    public ResponseEntity<OrderDto> addItem(@PathVariable Long oid, @RequestBody OrderItemInListDto item){
        return ResponseEntity.ok(orderService.addOrderItem(oid, service.getObjectFromDto(service.create(item))));
    }

/**
 * It creates order items and adds them to order
 * 
 * @param oid order id
 * @param item List<OrderItemInListDto>
 * @return ResponseEntity<OrderDto>
 */
    @Operation(summary = "creates order items and adds them to order")
    @PostMapping("/addbulk")
    public ResponseEntity<OrderDto> addItemBulk(@PathVariable Long oid, @RequestBody List<OrderItemInListDto> item){
        return ResponseEntity.ok(orderService.addOrderItem(oid, service.getObjectFromDto(service.create(item))));
    }

/**
 * Delete item in order
 * 
 * @param oid order id
 * @param id the id of the item to be deleted
 * @return The response entity is returning the orderDto.
 */
    @Operation(summary = "Delete item in order")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<OrderDto> deleteItem(@PathVariable Long oid, @PathVariable Long id){
        orderService.removeOrderItem(oid, id);
        service.delete(id);
        return ResponseEntity.ok(orderService.getByIdDto(oid));
    }

/**
 * Change quantity of item
 * 
 * @param oid order id
 * @param id the id of the order
 * @param item the object that is sent in the request body
 * @return ResponseEntity<OrderItemInListDto>
 */
    @Operation(summary = "change quantity of item")
    @PutMapping("/{id}/update")
    public ResponseEntity<OrderItemInListDto> update(@PathVariable Long oid, @PathVariable Long id, @RequestBody OrderItemInListDto item){
        if(item.getQuantity()<0){
            throw new InvalidDataAccessApiUsageException("quantity must be not negative");
        }
        return ResponseEntity.ok(service.update(id, item));
    }

}
