package com.fox.factory.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Float price;

    private String name;

    @OneToMany
    private Set<ProductImage> productImageSet;
    @Column(length = 255*10)
    private String description;

    @ManyToMany
    @JoinTable(
            name = "cat_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Catrgory> catrgories;

    private Float rating;
    private Integer rates;
    @OneToMany
    private Set<Comment> comments;

    public void addToCategories(Catrgory c){
        this.catrgories.add(c);
    }
    public void removeFromCategories(Catrgory c){
        this.catrgories.remove(c);
    }

    public void addImage(ProductImage image){
        this.productImageSet.add(image);
    }

    public void removeImage(ProductImage image){
        this.productImageSet.remove(image);
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }
}
