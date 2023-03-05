package com.fox.factory.service;

import com.fox.factory.entities.ProductImage;
import com.fox.factory.entities.dto.ProductImageDto;
import com.fox.factory.repositories.ProductImageRepository;
import com.fox.factory.service.mappers.ProductImageMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductImageService {
    private final ProductImageRepository repository;
    private final ProductImageMapper mapper;
    @Transactional
    public ProductImageDto create(ProductImageDto image){
        return mapper.toDto(repository.save(mapper.toEntity(image)));
    }
    @Transactional
    public ProductImageDto getById(Long id){
        return mapper.toDto(repository.findById(id).orElse(null));
    }
    @Transactional

    public ProductImageDto updateImg(Long id, ProductImageDto updated){
        var image = repository.findById(id).orElse(null);
        if (image==null)
            return null;
        return mapper.toDto(repository.save(mapper.partialUpdate(updated, image)));
    }

}
