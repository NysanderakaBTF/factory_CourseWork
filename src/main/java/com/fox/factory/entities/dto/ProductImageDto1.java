package com.fox.factory.entities.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * A DTO for the {@link com.fox.factory.entities.ProductImage} entity
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ProductImageDto1 implements Serializable {
    private final Long id;
    private final byte[] data;
    private final String name;
    private final String type;
}