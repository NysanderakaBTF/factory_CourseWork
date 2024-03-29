package com.fox.factory.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

/**
 * Order is a class that represents an order made by a user
 */
@Entity
@Getter
@Setter
@Table(name = "shop_orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<OrderItem> itemSet;
    @Column
    private Double totalPrice;
    @Column
    private Float discount;
    @Column
    private LocalDate creationDate;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order addOrderItem(OrderItem i){
        this.itemSet.add(i);
        this.totalPrice += i.getTotal();
        return this;
    }

    public Order setItems(Set<OrderItem> items){
        this.itemSet = items;
        return this;
    }

    public void removeOrderItem(OrderItem i){
        this.itemSet.remove(i);
    }

    public Order removeOrderItem(Long id){
        this.itemSet.removeIf(orderItem -> orderItem.getId() == id);
        return this;
    }



}
