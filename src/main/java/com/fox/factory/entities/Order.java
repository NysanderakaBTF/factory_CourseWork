package com.fox.factory.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private User user;
    @OneToMany
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

    public void addOrderItem(OrderItem i){
        this.itemSet.add(i);
    }

    public void removeOrderItem(OrderItem i){
        this.itemSet.remove(i);
    }



}
