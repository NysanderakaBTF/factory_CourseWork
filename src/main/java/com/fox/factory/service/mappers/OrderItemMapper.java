package com.fox.factory.service.mappers;

import com.fox.factory.entities.OrderItem;
import com.fox.factory.entities.dto.OrderItemInListDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {ProductMapper.class})
public interface OrderItemMapper {
    OrderItem toEntity(OrderItemInListDto orderItemInListDto);

    OrderItemInListDto toDto(OrderItem orderItem);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrderItem partialUpdate(OrderItemInListDto orderItemInListDto, @MappingTarget OrderItem orderItem);

}