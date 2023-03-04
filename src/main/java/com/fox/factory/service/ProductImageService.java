package com.fox.factory.service;

import com.fox.factory.entities.ProductImage;
import com.fox.factory.repositories.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductImageService {
    @Autowired
    private ProductImageRepository repository;

    public ProductImage create(ProductImage image){
        return repository.save(image);
    }
    public ProductImage getById(Long id){
        return repository.findById(id).orElse(null);
    }

    public ProductImage updateImg(Long id, ProductImage image){
        var a = repository.findById(id).orElse(null);
        if (a==null)
            return null;
        a.setData(image.getData());
        return repository.save(a);
    }

}
