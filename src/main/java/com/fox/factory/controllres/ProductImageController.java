package com.fox.factory.controllres;

import com.fox.factory.entities.ProductImage;
import com.fox.factory.entities.dto.ProductImageDto1;
import com.fox.factory.entities.dto.ProductImageDto1;
import com.fox.factory.service.ProductImageService;
import com.fox.factory.utils.ImageUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/images")
public class ProductImageController {
    private ProductImageService service;
    @Operation(summary = "Add image to a product")
    @PostMapping(value = "/{pid}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Set<ProductImageDto1>> createAndAddImageToProduct(@RequestParam("image") MultipartFile[] file, @PathVariable Long pid)
         throws IOException {
        Set<ProductImageDto1> productImageSet = new HashSet<>();
        for (MultipartFile image: file) {
            ProductImage img = ProductImage.builder()
                    .name(image.getOriginalFilename())
                    .type(image.getContentType())
                    .data(ImageUtil.compressImage(image.getBytes())).build();
            productImageSet.add(service.createFromObject(pid, img));
        }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(productImageSet);

    }

    @Operation(summary = "delete image by it's id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id){
        service.deleteImg(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "get image by id")
    @GetMapping("/{id}")
    public ResponseEntity<ProductImageDto1> viewImage(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }
//
//    @GetMapping(path = {"/get/image/{name}"})
//    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws IOException {
//
//        final Optional<Image> dbImage = imageRepository.findByName(name);
//
//        return ResponseEntity
//                .ok()
//                .contentType(MediaType.valueOf(dbImage.get().getType()))
//                .body(ImageUtility.decompressImage(dbImage.get().getImage()));
//    }
}
