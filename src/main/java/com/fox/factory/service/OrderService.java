package com.fox.factory.service;

import com.fox.factory.entities.Order;
import com.fox.factory.entities.OrderItem;
import com.fox.factory.entities.OrderStatus;
import com.fox.factory.entities.dto.OrderDto;
import com.fox.factory.exceptions.InchangableOrderException;
import com.fox.factory.repositories.OrderRepository;
import com.fox.factory.service.mappers.OrderItemMapper;
import com.fox.factory.service.mappers.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderItemMapper itemMapper;

    @Transactional
    public OrderDto create(OrderDto order) {
        return mapper.toDto(repository.save(mapper.toEntity(order)));
    }

    @Transactional
    public List<OrderDto> getAllByUserId(Long uid) {
        return repository.findAllByUser_Id(uid).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public List<OrderDto> getAllBetweenDates(LocalDate d1, LocalDate d2) {
        return repository.findAllByCreationDateBetween(d1, d2).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public OrderDto getByIdDto(Long id) {
        return mapper.toDto(repository.findById(id).orElse(null));
    }

    @Transactional
    public Order getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public OrderDto update(Long id, OrderDto upd) throws InchangableOrderException {
        var a = repository.findById(id);
        if (a.isPresent()) {
            var order = a.get();
            if (order.getStatus() != OrderStatus.IN_BUCKET) {
                throw new InchangableOrderException("Can't modify existing order" + id);
            }
            return mapper.toDto(repository.save(mapper.partialUpdate(upd, order)));
        }
        return null;
    }

    @Transactional
    public OrderDto addOrderItem(Long id, OrderItem oi) {
        return repository.findById(id)
                .map(order -> order.addOrderItem(oi))
                .map(repository::save)
                .map(mapper::toDto)
                .orElse(null);
    }

    @Transactional
    public OrderDto removeOrderItem(Long orderId, Long itemId) {
        return repository.findById(orderId)
                .map(order -> order.removeOrderItem(itemId))
                .map(repository::save)
                .map(mapper::toDto)
                .orElse(null);
//        var a = repository.findById(orderId).orElse(null);
//        if (a == null)
//            return null;
//        a.removeOrderItem(itemId);
//        return mapper.toDto(repository.save(a));
    }


}
