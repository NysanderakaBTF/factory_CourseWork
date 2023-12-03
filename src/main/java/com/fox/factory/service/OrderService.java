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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final OrderMapper mapper;

    private final UserService service;
    private final OrderItemMapper itemMapper;

    @Transactional
    public OrderDto create(OrderDto order) {
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = service.findByUsername(userName).orElseThrow();
        Order order1 = mapper.toEntity(order);
        order1.setUser(user);
        order1 = repository.save(order1);
        user.addOrder(order1);
        service.save(user);
        return mapper.toDto(order1);
    }
    @Transactional
    public OrderDto create(Order order) {
        return mapper.toDto(repository.save(order));
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
        return mapper.toDto(repository.findById(id).orElseThrow());
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
        Optional<Order> o = repository.findById(id);
        if (o.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No order with such id");
        }
        Order oo = o.get();
        oo.addOrderItem(oi);
        oo.setTotalPrice(oo.getTotalPrice() + oi.getTotal());
        oo = repository.save(oo);

        return mapper.toDto(oo);
    }

    @Transactional
    public OrderDto addOrderItem(Long id, List<OrderItem> oi) {
        return repository.findById(id)
                .map(order -> order.setItems(oi.stream().collect(Collectors.toSet())))
                .map(repository::save)
                .map(mapper::toDto)
                .orElse(null);
    }

    @Transactional
    public OrderDto removeOrderItem(Long orderId, Long itemId) {
        Optional<Order> o = repository.findById(orderId);
        if (o.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No order with such id");
        }
        Order oo = o.get();
        oo.removeOrderItem(itemId);
        Double price = 0D;
        for (var i : oo.getItemSet()){
            price += i.getTotal();
        }
        oo.setTotalPrice(price);
        oo = repository.save(oo);
        return mapper.toDto(oo);
    }


}
