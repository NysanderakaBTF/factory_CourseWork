package com.fox.factory.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
/**
 * ProductImage is a class that has a String data field, a String name field, a String type field, and
 * a Product product field.
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Lob
    @Column(name = "data", unique = false, nullable = false, length = 100000)
    private String data;
    @Column
    private String name;

    @Column(name = "type")
    private String type;

    @JoinColumn
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;
}