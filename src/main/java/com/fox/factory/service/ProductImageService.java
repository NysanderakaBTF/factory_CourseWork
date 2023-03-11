package com.fox.factory.service;

import com.fox.factory.entities.ProductImage;
import com.fox.factory.entities.dto.ProductImageDto1;
import com.fox.factory.entities.dto.ProductImageDto1;
import com.fox.factory.repositories.ProductImageRepository;
import com.fox.factory.service.mappers.ProductImageMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
public class ProductImageService {
    private final ProductImageRepository repository;
    private final ProductImageMapper mapper;

    private final ProductService productService;

    @Transactional
    public Set<ProductImageDto1> create(Long pid, ProductImageDto1 image){
        var img = repository.save(mapper.toEntity(image));
        return productService.addImageToProduct(pid, img).getProductImageSet();
    }
    
    @Transactional 
    public Set<ProductImageDto1> createFromObject(Long pid, ProductImage image){
        var img = repository.save(image);
        return productService.addImageToProduct(pid, img).getProductImageSet();
    }
    @Transactional
    public ProductImageDto1 getById(Long id){
        return mapper.toDto(repository.findById(id).orElse(null));
    }
    @Transactional

    public ProductImageDto1 updateImg(Long id, ProductImageDto1 updated){
        return repository.findById(id)
                .map(image -> mapper.partialUpdate(updated, image))
                .map(repository::save)
                .map(mapper::toDto)
                .orElse(null);
//
//        var image = repository.findById(id).orElse(null);
//        if (image==null)
//            return null;
//        return mapper.toDto(repository.save(mapper.partialUpdate(updated, image)));
    }

    @Transactional
    public void deleteImg(Long id){
        repository.deleteById(id);
    }

}
