package com.fox.factory.service;

import com.fox.factory.entities.OrderItem;
import com.fox.factory.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository repository;

    public OrderItem create(OrderItem orderItem){
        orderItem.setTotal(orderItem.getQuantity()*orderItem.getProduct().getPrice());
        return repository.save(orderItem);
    }
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
    public List<OrderItem> getAllByOrderId(Long id){
        return repository.findAllByOrder_Id(id);
    }
    public OrderItem getById(Long id){
        return repository.findById(id).orElse(null);
    }

}
