package com.fox.factory.entities.dto;

import com.fox.factory.entities.ProductImage;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * A DTO for the {@link ProductImage} entity
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ProductImageDto implements Serializable {
    private final Long id;
    private final byte[] data;
}