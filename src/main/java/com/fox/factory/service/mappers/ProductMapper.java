package com.fox.factory.service.mappers;

import com.fox.factory.entities.Product;
import com.fox.factory.entities.dto.ProductDetailDto;
import com.fox.factory.entities.dto.ProductListDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {ProductImageMapper.class, CatrgoryMapper.class, CommentMapper.class})
public interface ProductMapper {
    Product toEntity(ProductListDto productListDto);

    ProductListDto toDto(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductListDto productListDto, @MappingTarget Product product);

    Product toEntity1(ProductDetailDto productDetailDto);

    ProductDetailDto toDto1(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate1(ProductDetailDto productDetailDto, @MappingTarget Product product);

    Product toEntity2(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate2(Product product, @MappingTarget Product product);
}