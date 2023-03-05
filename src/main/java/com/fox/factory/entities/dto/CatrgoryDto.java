package com.fox.factory.entities.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * A DTO for the {@link com.fox.factory.entities.Catrgory} entity
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CatrgoryDto implements Serializable {
    private Long id;
    private String title;
    private String slug;
}