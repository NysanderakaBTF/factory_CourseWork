package com.fox.factory.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Product is a class that has a set of ProductImage, a set of Catrgory, a set of Comment, a Float
 * rating, an Integer rates, a Float price, a String name, a String description
 */
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

    @OneToMany
    @JoinColumn
    private Set<OrderItem> orderItemSet;


/**
 * This function adds a category to the list of categories
 * 
 * @param c The category to add to the list of categories.
 */
    public void addToCategories(Catrgory c){
        this.catrgories.add(c);
    }
/**
 * // Java
 *     public void removeFromCategories(Catrgory c){
 *         this.catrgories.remove(c);
 *     }
 * 
 * @param c The category to be removed from the list of categories.
 */
    public void removeFromCategories(Catrgory c){
        this.catrgories.remove(c);
    }

/**
 * It adds an image to the product
 * 
 * @param image the image to be added to the product
 * @return The Product object is being returned.
 */
    public Product addImage(ProductImage image){
        this.productImageSet.add(image);
        return this;
    }

/**
 * It removes an image from the product's image set
 * 
 * @param image the image to be removed
 * @return The Product object itself.
 */
    public Product removeImage(ProductImage image){
        productImageSet = productImageSet.stream().filter(image1 -> !Objects.equals(image1.getId(), image.getId())).collect(Collectors.toSet());
        return this;
    }

/**
 * This function adds a comment to the list of comments
 * 
 * @param comment The comment to be added to the list of comments.
 */
    public void addComment(Comment comment){
        this.comments.add(comment);
    }
/**
 * It takes an integer and updates the rating and rates fields of the object
 * 
 * @param a the rating that the user has given
 */
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
