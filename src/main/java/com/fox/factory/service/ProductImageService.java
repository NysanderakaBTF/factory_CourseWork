package com.fox.factory.service;

import com.fox.factory.entities.ProductImage;
import com.fox.factory.repositories.ProductImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductImageService {
    private final ProductImageRepository repository;
    @Transactional
    public ProductImage create(ProductImage image){
        return repository.save(image);
    }
    @Transactional
    public ProductImage getById(Long id){
        return repository.findById(id).orElse(null);
    }
    @Transactional

    public ProductImage updateImg(Long id, ProductImage image){
        var a = repository.findById(id).orElse(null);
        if (a==null)
            return null;
        a.setData(image.getData());
        return repository.save(a);
    }

}
