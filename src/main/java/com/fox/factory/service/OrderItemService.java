package com.fox.factory.service;

import com.fox.factory.entities.OrderItem;
import com.fox.factory.repositories.OrderItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemService {

    private final OrderItemRepository repository;
    @Transactional
    public OrderItem create(OrderItem orderItem){
        orderItem.setTotal(orderItem.getQuantity()*orderItem.getProduct().getPrice());
        return repository.save(orderItem);
    }
    @Transactional
    public void delete(Long id){
        repository.deleteById(id);
    }
    public OrderItem update(Long id, OrderItem item){
        var i = repository.findById(id).orElse(null);
        if (i!=null){
            i.setQuantity(item.getQuantity());
            i.setTotal(item.getQuantity()*item.getProduct().getPrice());
            return repository.save(i);
        }
        return null;
    }
    @Transactional
    public List<OrderItem> getAllByOrderId(Long id){
        return repository.findAllByOrder_Id(id);
    }
    @Transactional
    public OrderItem getById(Long id){
        return repository.findById(id).orElse(null);
    }

}
