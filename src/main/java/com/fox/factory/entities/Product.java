package com.fox.factory.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;

    private Float price;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private Set<ProductImage> productImageSet;
    @Column(length = 255*10)
    private String description;

    @ManyToMany
    @JoinTable(
            name = "cat_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Catrgory> catrgories;

    private Float rating = 0.0F;
    private Integer rates = 0;
    @OneToMany
    @JoinColumn
    private Set<Comment> comments;

    public void addToCategories(Catrgory c){
        this.catrgories.add(c);
    }
    public void removeFromCategories(Catrgory c){
        this.catrgories.remove(c);
    }

    public Product addImage(ProductImage image){
        this.productImageSet.add(image);
        return this;
    }

    public Product removeImage(ProductImage image){
        productImageSet = productImageSet.stream().filter(image1 -> !Objects.equals(image1.getId(), image.getId())).collect(Collectors.toSet());
        return this;
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }
    public void updateRate(Integer a){
        if (rates == null){
            rates = 0;
        }
        if(rating == null){
            rating = 0f;
        }
        rating = (rating*rates + a )/(rates+1);
        rates++;
    }
}
