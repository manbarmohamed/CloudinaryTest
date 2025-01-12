package com.example.cloudinary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductCreateDTO {
    private String name;
    private String description;
    private Double price;
}