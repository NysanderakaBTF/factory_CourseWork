package com.fox.factory.service;

import com.fox.factory.entities.Order;
import com.fox.factory.entities.OrderItem;
import com.fox.factory.entities.OrderStatus;
import com.fox.factory.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fox.factory.exceptions.InchangableOrderException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    @Transactional
    public Order create(Order order){
        return repository.save(order);
    }
    @Transactional
    public List<Order> getAllByUserId(Long uid){
        return repository.findAllByUser_Id(uid);
    }
    @Transactional
    public List<Order> getAllBetweenDates(LocalDate d1, LocalDate d2){
        return repository.findAllByCreationDateBetween(d1,d2);
    }
    @Transactional
    public Order getById(Long id){
        return repository.findById(id).orElse(null);
    }
    @Transactional
    public void delete(Long id){
        repository.deleteById(id);
    }
    @Transactional
    public Order update(Long id, Order upd) throws InchangableOrderException {
        var a = repository.findById(id).orElse(null);
        if(a != null){
           if(a.getStatus()!= OrderStatus.IN_BUCKET) {
               throw new InchangableOrderException("Can't modify existing order"+id);
           }
           a.setItemSet(upd.getItemSet());
           a.setStatus(upd.getStatus());
           a.setDiscount(upd.getDiscount());
           a.setTotalPrice(upd.getTotalPrice());
           return repository.save(a);
        }
        return null;
    }
    @Transactional
    public Order addOrderItem(Long id, OrderItem oi){
        var a = repository.findById(id).orElse(null);
        if(a==null)
            return a;
        a.addOrderItem(oi);
        return repository.save(a);
    }
    @Transactional
    public Order removeOrderItem(Long id, OrderItem oi){
        var a = repository.findById(id).orElse(null);
        if(a==null)
            return a;
        a.removeOrderItem(oi);
        return repository.save(a);
    }


}
