package com.fox.factory.service;

import com.fox.factory.entities.Catrgory;
import com.fox.factory.entities.Comment;
import com.fox.factory.entities.Product;
import com.fox.factory.entities.dto.*;
import com.fox.factory.repositories.CommentRepository;
import com.fox.factory.repositories.ProductRepository;
import com.fox.factory.service.mappers.CatrgoryMapper;
import com.fox.factory.service.mappers.CommentMapper;
import com.fox.factory.service.mappers.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository repository;
    private ProductMapper mapper;
    private CommentMapper commentMapper;
    private CatrgoryMapper catrgoryMapper;

    private CommentService commentService;

    @Transactional
    public ProductDetailDto create(ProductDetailDto product){
        return mapper.toProductDetailDto(repository.save(mapper.fromProductDetail(product)));
    }
    @Transactional
    public void delete(Long id) {repository.deleteById(id);}
    @Transactional
    public ProductDetailDto commentProduct(Long prooductId, CommentCreateDto comment){
        return mapper.toProductDetailDto(repository.findById(prooductId).map(product -> {
            Comment commentObj = commentMapper.toEntityCreate(comment);
            commentObj.setProduct(product);
            commentService.create(commentMapper.toDtoCreate(commentObj));
            product.updateRate(comment.getRate());
            return product;
        }).orElse(null));
    }
    @Transactional
    public List<ProductListDto> findByCategories(List<CatrgoryDto> cat){
        return repository.filterProductByCategory(cat.stream()
                        .map(catrgoryMapper::toEntity)
                        .collect(Collectors.toList()))
                        .stream().map(mapper::toDto)
                        .collect(Collectors.toList());
    }
    @Transactional
    public List<ProductListDto> findByName(String namePart){
        return repository.filterProductByName(namePart).stream().map(mapper::toDto).collect(Collectors.toList());
    }
    @Transactional
    public ProductDetailDto update(Long id, ProductDetailDto upd){
        return mapper.toProductDetailDto(repository.findById(id).map(product ->
        {
            return repository.save(mapper.partialUpdate1(upd, product));
        }).orElse(null));
    }
}
