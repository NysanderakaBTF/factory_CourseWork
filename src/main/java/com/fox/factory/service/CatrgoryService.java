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
        return mapper.toDto(repository.save(mapper.toEntity(category)));
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
        var catrgory = repository.findById(id).orElse(null);
        if (catrgory == null)
            return null;
        return mapper.toDto(repository.save(mapper.partialUpdate(updated, catrgory)));

    }
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
