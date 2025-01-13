package com.example.cloudinary;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final CloudinaryService cloudinaryService;
    private final ProductMapper productMapper;

    @GetMapping
    @Operation(summary = "Get all products with their images")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(productMapper.toDTOList(products));
    }

//    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<ProductDTO> createProduct(
//                @ModelAttribute ProductCreateDTO productCreateDTO,
//            @RequestPart(value = "images", required = false) List<MultipartFile> images
//    ) {
//        ProductDTO createdProduct = productService.createProduct(productCreateDTO, images);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
//    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Create new product with images")
    public ResponseEntity<ProductDTO> createProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        ProductCreateDTO productCreateDTO = new ProductCreateDTO();
        productCreateDTO.setName(name);
        productCreateDTO.setDescription(description);
        productCreateDTO.setPrice(price);

        log.info("Received product data: name={}, description={}, price={}", name, description, price);

        ProductDTO createdProduct = productService.createProduct(productCreateDTO, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

}