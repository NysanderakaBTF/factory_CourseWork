package com.fox.factory.service;

import com.fox.factory.entities.Catrgory;
import com.fox.factory.repositories.CatrgoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CatrgoryService {

    private final CatrgoryRepository repository;

    @Transactional
    public Catrgory create(Catrgory a) {
        return repository.save(a);
    }
    @Transactional
    public Catrgory findByTitle(String title) {
        return repository.findByTitle(title);
    }
    @Transactional
    public Catrgory findById(Long id) {
        return repository.findById(id).orElse(null);
    }
    @Transactional
    public List<Catrgory> findAllByTitleContains(String title) {
        return repository.findAllByTitleContaining(title);
    }
    @Transactional
    public Catrgory update(Long id, Catrgory catrgory) {
        var a = repository.findById(id).orElse(null);
        if (a == null)
            return null;
        a.setTitle(catrgory.getTitle());
        a.slugify();
        a.setProductSet(catrgory.getProductSet());
        return repository.save(a);

    }
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
