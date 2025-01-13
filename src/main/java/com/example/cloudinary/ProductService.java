package com.example.cloudinary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CloudinaryService cloudinaryService;

    private static final Logger log = (Logger) LoggerFactory.getLogger(ProductService.class);


    public ProductDTO createProduct(ProductCreateDTO productCreateDTO, List<MultipartFile> images) {
        Product product = productMapper.createDTOToEntity(productCreateDTO);
        log.info("Produit avant sauvegarde : {}", product);

        List<Image> uploadedImages = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                Map<String, String> uploadResult = cloudinaryService.uploadImage(image);

                Image uploadedImage = new Image();
                uploadedImage.setUrl(uploadResult.get("url"));
                uploadedImage.setPublicId(uploadResult.get("public_id"));
                uploadedImage.setProduct(product);

                uploadedImages.add(uploadedImage);
            }
        }

        product.setImages(uploadedImages);

        // Sauvegarder le produit avec ses images dans la base de données
        Product savedProduct = productRepository.save(product);

        log.info("Produit après sauvegarde : {}", savedProduct);

        // Retourner un DTO du produit
        return productMapper.toDTO(savedProduct);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}