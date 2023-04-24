package com.fox.factory.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * An OrderItem is a product that is part of an order
 */
@Entity
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private Integer quantity;

    @OneToOne
    private Product product;

    @Column
    private Float total;
    @ManyToOne
    private Order order;

}
