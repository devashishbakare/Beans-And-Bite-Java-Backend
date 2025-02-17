package com.beansAndBite.beansAndBite.dto;

import com.beansAndBite.beansAndBite.enums.Category;
import com.beansAndBite.beansAndBite.enums.ProductType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {

    @NotBlank(message = "Name is mandatory")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @Size(max = 500, message = "Product info cannot exceed 500 characters")
    private String productInfo;

    @NotBlank(message = "Product details are mandatory")
    @Size(max = 500, message = "Product details cannot exceed 500 characters")
    private String productDetails;

    private String category;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotBlank(message = "Product cart image URL is mandatory")
    @Size(max = 500, message = "Product cart image URL cannot exceed 500 characters")
    @Pattern(
        regexp = "^(https?:\\/\\/)?([\\w.-]+)?([\\w]+)(\\.[\\w]+)+(\\/.*)?$",
        message = "Invalid URL format for product cart image"
    )
    private String productCartImage;

    @NotBlank(message = "Product details image URL is mandatory")
    @Size(max = 500, message = "Product details image URL cannot exceed 500 characters")
    @Pattern(
        regexp = "^(https?:\\/\\/)?([\\w.-]+)?([\\w]+)(\\.[\\w]+)+(\\/.*)?$",
        message = "Invalid URL format for product details image"
    )
    private String productDetailsImage;

    private String productType;

}
