package com.fox.factory.controllres;

import com.fox.factory.entities.dto.CatrgoryDto;
import com.fox.factory.entities.dto.CommentCreateDto;
import com.fox.factory.entities.dto.ProductDetailDto;
import com.fox.factory.entities.dto.ProductListDto;
import com.fox.factory.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
/**
 * This class is a controller that handles all the requests that are related to products.
 */

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

/**
 * Create a new product
 * 
 * @param dto The object that will be created.
 * @return A ResponseEntity with a ProductDetailDto
 */
    @Operation(summary = "Create a new product")
    @PostMapping("/new")
    @PreAuthorize("hasAuthority('add_product')")
    public ResponseEntity<ProductDetailDto> createProd(@RequestBody ProductDetailDto dto){
        return ResponseEntity.ok(service.create(dto));
    }

/**
 * Delete product
 * 
 * @param id The id of the product to be deleted
 * @return ResponseEntity.noContent().build();
 */
    @Operation(summary = "Delete product")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('add_product')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

/**
 * This function returns a product detail object in the form of a DTO (Data Transfer Object)
 * 
 * @param id The id of the product to be retrieved
 * @return A ResponseEntity with a ProductDetailDto
 */
    @Operation(summary = "View Product Details")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailDto> getProduct(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

/**
 * Filter by categories
 * 
 * @param catsList [{id: 1}, {id: 2}]
 * @return A list of ProductListDto objects
 */
    @Operation(summary = "Filter by categories")
    @PostMapping("/filter/cat")
    public ResponseEntity<List<ProductListDto>> categoryFilter(@RequestBody CatrgoryDto[] catsList){
        return ResponseEntity.ok(service.findByCategories(Arrays.stream(catsList).toList()));
    }

/**
 * Get all products
 * 
 * @param pageable the pageable object that will be used to get the page number and size
 * @return A list of ProductListDto objects
 */
    @Operation(summary = "get all products")
    @GetMapping("/all")
    public ResponseEntity<List<ProductListDto>> allProducts(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(service.getAll());
    }
/**
 * Search by name
 * 
 * @param title The name of the product
 * @return A list of ProductListDto objects.
 */

    @Operation(summary = "Search by name")
    @PostMapping("/filter/name")
    public ResponseEntity<List<ProductListDto>> nameFilter(@RequestParam String title){
        return ResponseEntity.ok(service.findByName(title));
    }

/**
 * Searhc by both
 * 
 * @param title String
 * @param dtos List of categories
 * @param pageabl Pageable
 * @return A list of ProductListDto
 */
    @Operation(summary = "Searhc by both")
    @PostMapping("/filter/both")
    public ResponseEntity<List<ProductListDto>> bothFilter(@Nullable @RequestParam String title,
                                                           @Nullable @RequestBody List<CatrgoryDto> dtos,
                                                           @Nullable @ParameterObject Pageable pageabl) {
        return ResponseEntity.ok(service.findByCategoriesAndNames(dtos, title, pageabl));
    }

/**
 * Leave comment on a product
 * 
 * @param id The id of the product
 * @param dto The object that will be used to create the comment.
 * @return A product detail dto
 */
    @Operation(summary = "Leave comment on a product")
    @PostMapping("/{id}/comments")
    public ResponseEntity<ProductDetailDto> leaveCommentOnAProduct(HttpServletRequest req,
                                                                   @PathVariable Long id,
                                                                   @RequestBody CommentCreateDto dto){
        return ResponseEntity.ok(service.commentProduct(req, id, dto));
    }

/**
 * Update informaion on product
 * 
 * @param id The id of the product to be updated
 * @param detailDto This is the object that will be passed to the method.
 * @return A ResponseEntity with a ProductDetailDto
 */
    @Operation(summary = "Update informaion on product")
    @PutMapping("/{id}/update")
    @PreAuthorize("hasAuthority('add_product')")
    public ResponseEntity<ProductDetailDto> update (@PathVariable Long id, @RequestBody ProductDetailDto detailDto){
        return ResponseEntity.ok(service.update(id, detailDto));
    }

}
