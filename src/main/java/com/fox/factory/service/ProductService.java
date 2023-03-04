package com.fox.factory.service;

import com.fox.factory.entities.Comment;
import com.fox.factory.entities.Product;
import com.fox.factory.repositories.CommentRepository;
import com.fox.factory.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private CommentService commentService;

    public Product create(Product product){
        return repository.save(product);
    }

    public void delete(Long id) {repository.deleteById(id);}

    public void commentProduct(Long prooductId, Comment comment){
        repository.findById(prooductId).map(product -> {
            comment.setProduct(product);
            commentService.create(comment);
            return product;
        });
    }

    public Product

}
