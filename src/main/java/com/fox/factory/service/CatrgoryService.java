package com.fox.factory.service;

import com.fox.factory.entities.Catrgory;
import com.fox.factory.repositories.CatrgoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatrgoryService {
    @Autowired
    private CatrgoryRepository repository;

    public Catrgory create(Catrgory a) {
        return repository.save(a);
    }

    public Catrgory findByTitle(String title) {
        return repository.findByTitle(title);
    }

    public Catrgory findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Catrgory> findAllByTitleContains(String title) {
        return repository.findAllByTitleContaining(title);
    }

    public Catrgory update(Long id, Catrgory catrgory) {
        var a = repository.findById(id).orElse(null);
        if (a != null) {
            a.setTitle(catrgory.getTitle());
            a.slugify();
            a.setProductSet(catrgory.getProductSet());
            return repository.save(a);
        }
        return null;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
