package com.fox.factory.entities.dto;

import com.fox.factory.entities.OrderItem;
import com.fox.factory.entities.Product;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * A DTO for the {@link OrderItem} entity
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class OrderItemInListDto implements Serializable {
    private final Long id;
    private final Integer quantity;
    private final Product product;
    private final Float total;
}