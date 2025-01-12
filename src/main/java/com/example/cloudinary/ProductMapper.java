package com.example.cloudinary;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "images", source = "images")
    ProductDTO toDTO(Product product);

    @Mapping(target = "images", ignore = true)
    Product toEntity(ProductDTO productDTO);

    List<ProductDTO> toDTOList(List<Product> products);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "images", ignore = true)
    Product createDTOToEntity(ProductCreateDTO createDTO);

    ProductCreateDTO toCreateDTO(Product product);

    // Méthodes de mappage pour les images
    default ImageDTO imageToImageDTO(Image image) {
        if (image == null) return null;

        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(image.getId());
        imageDTO.setUrl(image.getUrl());
        imageDTO.setPublicId(image.getPublicId());
        return imageDTO;
    }

    default Image imageDTOToImage(ImageDTO imageDTO) {
        if (imageDTO == null) return null;

        Image image = new Image();
        image.setId(imageDTO.getId());
        image.setUrl(imageDTO.getUrl());
        image.setPublicId(imageDTO.getPublicId());
        return image;
    }
}
