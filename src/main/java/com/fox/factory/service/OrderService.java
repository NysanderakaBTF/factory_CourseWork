package com.fox.factory.service;

import com.fox.factory.entities.Order;
import com.fox.factory.entities.OrderItem;
import com.fox.factory.entities.OrderStatus;
import com.fox.factory.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fox.factory.exeptions.InchangableOrderExeption;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    public Order create(Order order){
        return repository.save(order);
    }

    public List<Order> getAllByUserId(Long uid){
        return repository.findAllByUser_Id(uid);
    }

    public List<Order> getAllBetweenDates(LocalDate d1, LocalDate d2){
        return repository.findAllByCreationDateBetween(d1,d2);
    }
    public Order getById(Long id){
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public Order update(Long id, Order upd) throws InchangableOrderExeption {
        var a = repository.findById(id).orElse(null);
        if(a != null){
           if(a.getStatus()!= OrderStatus.IN_BUCKET) {
               throw new InchangableOrderExeption("Can't modify existing order"+id);
           }
           if(a!=null) {
               a.setItemSet(upd.getItemSet());
               a.setStatus(upd.getStatus());
               a.setDiscount(upd.getDiscount());
               a.setTotalPrice(upd.getTotalPrice());
               return repository.save(a);
           }
        }
        return null;
    }

    public Order addOrderItem(Long id, OrderItem oi){
        var a = repository.findById(id).orElse(null);
        if(a==null)
            return a;
        a.addOrderItem(oi);
        return repository.save(a);
    }

    public Order removeOrderItem(Long id, OrderItem oi){
        var a = repository.findById(id).orElse(null);
        if(a==null)
            return a;
        a.removeOrderItem(oi);
        return repository.save(a);
    }


}
