package com.fox.factory.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.text.Normalizer;
import java.util.Set;

/**
 * The class Catrgory has a field title which is unique and slug which is also unique
 */
@Getter
@Setter
@Entity
public class Catrgory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true)
    private String title;
    @Column(unique = true)
    private String slug;

    @ManyToMany
    @Column(name = "products_in_cat")
    private Set<Product> productSet;

    public String slugify() {
        String a = Normalizer
                .normalize(this.title, Normalizer.Form.NFD)
                .replace("[^\\w\\s-]", "") // Remove all non-word, non-space or non-dash characters
                .replace('-', ' ') // Replace dashes with spaces
                .trim() // Trim leading/trailing whitespace (including what used to be leading/trailing dashes)
                .replace("\\s+", "-") // Replace whitespace (including newlines and repetitions) with single dashes
                .toLowerCase();// Lowercase the final results
        slug = a;
        return a;
    }

}
