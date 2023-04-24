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
/**
 * It takes a CatrgoryDto object as a parameter, validates it, and then passes it to the service layer
 * to be saved.
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    private final CatrgoryService service;

/**
 * It takes a CatrgoryDto object as a parameter, validates it, and then passes it to the service layer
 * to be saved
 * 
 * @param cat The object that will be sent to the server.
 * @return A ResponseEntity object is being returned.
 */
    @PostMapping("/new")
    @PreAuthorize("hasAuthority('add_product')")
    public ResponseEntity<CatrgoryDto> saveCategory(@Valid @RequestBody CatrgoryDto cat){
        return ResponseEntity.ok(service.create(cat));
    }

/**
 * It returns a list of all the categories in the database
 * 
 * @return A list of CategoryDto objects.
 */
    @GetMapping("/all")
    public ResponseEntity<List<CatrgoryDto>> allCats(){
        return ResponseEntity.ok(service.getAll());
    }

/**
 * It deletes a category from the database
 * 
 * @param id The id of the category to be deleted.
 * @return A ResponseEntity object is being returned.
 */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('add_product')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
