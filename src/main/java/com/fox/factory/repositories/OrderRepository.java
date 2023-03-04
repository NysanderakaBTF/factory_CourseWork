package com.fox.factory.repositories;

import com.fox.factory.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCreationDateBetween(LocalDate creationDate, LocalDate creationDate2);

    List<Order> findAllByUser_Id(Long user_id);
}