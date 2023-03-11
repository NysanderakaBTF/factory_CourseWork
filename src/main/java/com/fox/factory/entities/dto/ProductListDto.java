package com.fox.factory.entities.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.fox.factory.entities.Product} entity
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ProductListDto implements Serializable {
    private final Long id;
    private final Float price;
    private final String name;
    private final Set<ProductImageDto1> productImageSet;
    private final Set<CatrgoryDto> catrgories;
    private final Float rating;
}