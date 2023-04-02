package com.fox.factory.service.mappers;

import com.fox.factory.entities.Order;
import com.fox.factory.entities.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {OrderItemMapper.class})
public abstract class OrderMapper {
    @Autowired
    protected UserMapper userMapper;


    public abstract Order toEntity(OrderDto orderDto);
    @Mapping(target = "userInOrder", expression = "java(userMapper.toDto1(order.getUser()))")
    public abstract OrderDto toDto(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Order partialUpdate(OrderDto orderDto, @MappingTarget Order order);
}