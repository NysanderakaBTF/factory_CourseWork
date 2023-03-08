package com.fox.factory.service;

import com.fox.factory.entities.OrderItem;
import com.fox.factory.entities.dto.OrderItemInListDto;
import com.fox.factory.repositories.OrderItemRepository;
import com.fox.factory.service.mappers.OrderItemMapper;
import com.fox.factory.service.mappers.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderItemService {

    private final OrderItemRepository repository;
    private final OrderItemMapper mapper;
    @Transactional
    public OrderItemInListDto create(OrderItemInListDto orderItem){
        OrderItem item = mapper.toEntity(orderItem);
        item.setTotal(orderItem.getQuantity()*orderItem.getProduct().getPrice());
        return mapper.toDto(repository.save(item));
    }
    @Transactional
    public void delete(Long id){
        repository.deleteById(id);
    }
    public OrderItemInListDto update(Long id, OrderItemInListDto updated){
        var item = repository.findById(id).orElse(null);
        if (item!=null){
            return mapper.toDto(repository.save(
                    mapper.partialUpdate(updated, item)
            ));
        }
        return null;
    }
    @Transactional
    public List<OrderItemInListDto> getAllByOrderId(Long id){
        return repository.findAllByOrder_Id(id).stream().map(mapper::toDto).collect(Collectors.toList());
    }
    @Transactional
    public OrderItemInListDto getById(Long id){
        return mapper.toDto(repository.findById(id).orElse(null));
    }

    public OrderItem getObjectFromDto(OrderItemInListDto item){
        return mapper.toEntity(item);
    }

}
