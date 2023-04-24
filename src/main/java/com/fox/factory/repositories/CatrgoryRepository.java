package com.fox.factory.repositories;

import com.fox.factory.entities.Catrgory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// A repository for the Catrgory entity.
public interface CatrgoryRepository extends JpaRepository<Catrgory, Long> {
    Catrgory findByTitle(String title);
    List<Catrgory> findAllByTitleContaining(String parttitle);
}