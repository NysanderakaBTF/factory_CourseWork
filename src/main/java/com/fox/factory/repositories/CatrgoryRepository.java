package com.fox.factory.repositories;

import com.fox.factory.entities.Catrgory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatrgoryRepository extends JpaRepository<Catrgory, Long> {
    Catrgory findByTitle(String title);
    List<Catrgory> findAllByTitleContaining(String parttitle);
}