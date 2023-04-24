package com.fox.factory.service;

import com.fox.factory.entities.Catrgory;
import com.fox.factory.entities.dto.CatrgoryDto;
import com.fox.factory.repositories.CatrgoryRepository;
import com.fox.factory.service.mappers.CatrgoryMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CatrgoryService {

    private final CatrgoryRepository repository;
    private final CatrgoryMapper mapper;

    @Transactional
    public CatrgoryDto create(CatrgoryDto category) {
        var cat = mapper.toEntity(category);
        if(cat.getSlug()==null)
            cat.setSlug(cat.slugify());
        return mapper.toDto(repository.save(cat));
    }
    @Transactional
    public CatrgoryDto findByTitle(String title) {
        return mapper.toDto(repository.findByTitle(title));
    }
    @Transactional
    public CatrgoryDto findById(Long id) {
        return mapper.toDto(repository.findById(id).orElse(null));
    }
    @Transactional
    public List<CatrgoryDto> findAllByTitleContains(String title) {
        return repository.findAllByTitleContaining(title).stream().map(mapper::toDto).collect(Collectors.toList());
    }
    @Transactional
    public CatrgoryDto update(Long id, CatrgoryDto updated) {
        return repository.findById(id)
                .map(catrgory -> mapper.partialUpdate(updated, catrgory))
                .map(repository::save)
                .map(mapper::toDto)
                .orElse(null);
    }
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public List<CatrgoryDto> getAll(){return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());}

}
