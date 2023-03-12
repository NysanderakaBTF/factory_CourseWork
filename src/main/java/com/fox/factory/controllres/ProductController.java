package com.fox.factory.controllres;

import com.fox.factory.entities.dto.CatrgoryDto;
import com.fox.factory.entities.dto.CommentCreateDto;
import com.fox.factory.entities.dto.ProductDetailDto;
import com.fox.factory.entities.dto.ProductListDto;
import com.fox.factory.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    @Operation(summary = "Create a new product")
    @PostMapping("/new")
    public ResponseEntity<ProductDetailDto> createProd(@RequestBody ProductDetailDto dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Delete product")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "View Product Details")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailDto> getProduct(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Filter by categories")
    @PostMapping("/filter/cat")
    public ResponseEntity<List<ProductListDto>> categoryFilter(@RequestBody CatrgoryDto[] catsList){
        return ResponseEntity.ok(service.findByCategories(Arrays.stream(catsList).toList()));
    }

    @Operation(summary = "get all products")
    @GetMapping("/all")
    public ResponseEntity<List<ProductListDto>> allProducts(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Search by name")
    @PostMapping("/filter/name")
    public ResponseEntity<List<ProductListDto>> nameFilter(@RequestParam String title){
        return ResponseEntity.ok(service.findByName(title));
    }

    @Operation(summary = "Searhc by both")
    @PostMapping("/filter/both")
    public ResponseEntity<List<ProductListDto>> bothFilter(@Nullable @RequestParam String title,
                                                           @Nullable @RequestBody List<CatrgoryDto> dtos,
                                                           @Nullable @ParameterObject Pageable pageabl) {
        return ResponseEntity.ok(service.findByCategoriesAndNames(dtos, title, pageabl));
    }

    @Operation(summary = "Leave comment on a product")
    @PostMapping("/{id}/comments")
    public ResponseEntity<ProductDetailDto> leaveCommentOnAProduct(@PathVariable Long id,
                                                                   @RequestBody CommentCreateDto dto){
        return ResponseEntity.ok(service.commentProduct(id, dto));
    }

    @Operation(summary = "Update informaion on product")
    @PutMapping("/{id}/update")
    public ResponseEntity<ProductDetailDto> update (@PathVariable Long id, @RequestBody ProductDetailDto detailDto){
        return ResponseEntity.ok(service.update(id, detailDto));
    }

}
