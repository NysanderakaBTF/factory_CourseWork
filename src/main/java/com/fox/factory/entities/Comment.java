package com.fox.factory.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(length = 255*15)
    private String text;


    private boolean isPublished;
    @ManyToMany
    private Set<Comment> subComments;

    @ManyToOne
    @JoinColumn(referencedColumnName = "user_id")
    private User author;

    private LocalDate publishDate;
    @ManyToOne
    @JoinColumn(referencedColumnName = "product_id")
    private Product product;

    private Integer rate;

    public Comment addToSubComments(Comment m){
        subComments.add(m);
        return this;
    }

    public Comment setPublished(boolean isPublished){
        this.isPublished = isPublished;
        return this;
    }
}
