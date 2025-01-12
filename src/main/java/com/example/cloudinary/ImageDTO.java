package com.example.cloudinary;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ImageDTO {
    private Long id;
    private String url;
    private String publicId;
}