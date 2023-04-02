package com.fox.factory.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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
    @ManyToOne
    private Product product;
}