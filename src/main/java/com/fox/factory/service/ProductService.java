package com.fox.factory.service;

import com.fox.factory.entities.Catrgory;
import com.fox.factory.entities.Comment;
import com.fox.factory.entities.Product;
import com.fox.factory.repositories.CommentRepository;
import com.fox.factory.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository repository;

    private CommentService commentService;

    @Transactional
    public Product create(Product product){
        return repository.save(product);
    }
    @Transactional
    public void delete(Long id) {repository.deleteById(id);}
    @Transactional
    public void commentProduct(Long prooductId, Comment comment){
        repository.findById(prooductId).map(product -> {
            comment.setProduct(product);
            commentService.create(comment);
            product.updateRate(comment.getRate());
            return product;
        });
    }
    @Transactional
    public List<Product> findByCategories(List<Catrgory> cat){
        return repository.filterProductByCategory(cat);
    }
    @Transactional
    public List<Product> findByName(String namePart){
        return repository.filterProductByName(namePart);
    }
    @Transactional
    public Product update(Long id, Product upd){
        return repository.findById(id).map(product ->
        {
            product.setComments(upd.getComments());
            product.setName(upd.getName());
            product.setPrice(upd.getPrice());
            product.setRates(upd.getRates());
            product.setProductImageSet(upd.getProductImageSet());
            product.setDescription(upd.getDescription());
            product.setPrice(upd.getPrice());
            product.setComments(upd.getComments());
            return repository.save(product);
        }).orElse(null);
    }
}
