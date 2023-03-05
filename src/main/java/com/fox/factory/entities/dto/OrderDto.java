package com.fox.factory.entities.dto;

import com.fox.factory.entities.OrderItem;
import com.fox.factory.entities.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO for the {@link com.fox.factory.entities.Order} entity
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class OrderDto implements Serializable {
    private final Long id;
    private final UserInOrderDto userInOrder;
    private final Set<OrderItemInListDto> itemSet;
    private final Double totalPrice;
    private final Float discount;
    private final LocalDate creationDate;
    private final OrderStatus status;
}