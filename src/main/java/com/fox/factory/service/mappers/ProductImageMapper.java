package com.fox.factory.service.mappers;

import com.fox.factory.entities.ProductImage;
import com.fox.factory.entities.dto.ProductImageDto1;
import com.fox.factory.entities.dto.ProductImageDto1;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductImageMapper {
    ProductImage toEntity(ProductImageDto1 ProductImageDto1);

    ProductImageDto1 toDto(ProductImage productImage);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductImage partialUpdate(ProductImageDto1 ProductImageDto1, @MappingTarget ProductImage productImage);

}