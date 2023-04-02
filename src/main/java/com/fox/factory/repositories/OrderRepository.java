package com.fox.factory.repositories;

import com.fox.factory.entities.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"user"})
    Optional<Order> findById(Long id);

    List<Order> findAllByCreationDateBetween(LocalDate creationDate, LocalDate creationDate2);

    List<Order> findAllByUser_Id(Long user_id);
}