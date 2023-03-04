package com.fox.factory.repositories;

import com.fox.factory.entities.Order;
import com.fox.factory.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllByOrder_Id(Long id);
}