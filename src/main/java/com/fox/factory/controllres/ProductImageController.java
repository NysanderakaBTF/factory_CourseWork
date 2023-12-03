package com.fox.factory.controllres;

import com.fox.factory.entities.ProductImage;
import com.fox.factory.entities.dto.ProductImageDto1;
import com.fox.factory.entities.dto.ProductImageDto1;
import com.fox.factory.repositories.ProductRepository;
import com.fox.factory.service.ProductImageService;
import com.fox.factory.service.ProductService;
import com.fox.factory.utils.ImageUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/images")
/**
 * It takes a product id and an array of images, creates a product image object from each image, and
 * adds it to the product
 */
public class ProductImageController {
    private ProductImageService service;
    private ProductRepository productRepository;
/**
 * It takes a product id and an array of images, creates a product image object from each image, and
 * adds it to the product
 * 
 * @param file the image file
 * @param pid product id
 * @return A set of ProductImageDto1 objects.
 */
    @Operation(summary = "Add image to a product")
    @PostMapping(value = "/{pid}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('add_product')")
    public ResponseEntity<Set<ProductImageDto1>> createAndAddImageToProduct(@RequestParam("image") MultipartFile[] file, @PathVariable Long pid)
         throws IOException {
        Set<ProductImageDto1> productImageSet = new HashSet<>();
        var product = productRepository.findById(pid);
        if (product.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such product for id");
        }
        var p = product.get();
        System.out.println(p);
        for (MultipartFile image: file) {
            ProductImage img = ProductImage.builder()
                    .name(image.getOriginalFilename())
                    .type(image.getContentType())
                    .product(p)
                    .data("data:"+image.getContentType()+";base64,"+ImageUtil.compressImage(image.getInputStream().readAllBytes())).build();
            productImageSet.add(service.createFromObject(pid, img));
        }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(productImageSet);
    }

/**
 * Delete image by it's id
 * 
 * @param id the id of the image to be deleted
 * @return ResponseEntity.noContent().build();
 */
    @Operation(summary = "delete image by it's id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('add_product')")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id){
        service.deleteImg(id);
        return ResponseEntity.noContent().build();
    }
/**
 * Get image by id
 * 
 * @param id The id of the image to be retrieved
 * @return A ResponseEntity with a ProductImageDto1 object.
 */
    @Operation(summary = "get image by id")
    @GetMapping("/{id}")
    public ResponseEntity<ProductImageDto1> viewImage(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }
}
