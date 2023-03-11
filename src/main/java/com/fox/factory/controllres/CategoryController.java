package com.fox.factory.controllres;

import com.fox.factory.entities.dto.CatrgoryDto;

import com.fox.factory.service.CatrgoryService;
import com.fox.factory.validation.OnCreate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    private final CatrgoryService service;

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('write') and (hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('DEVELOPER'))")
    @Validated(OnCreate.class)
    public ResponseEntity<CatrgoryDto> saveCategory(@Valid @RequestBody CatrgoryDto cat){
        return ResponseEntity.ok(service.create(cat));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<List<CatrgoryDto>> allCats(){
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
