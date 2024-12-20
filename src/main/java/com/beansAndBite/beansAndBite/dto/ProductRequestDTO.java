package com.beansAndBite.beansAndBite.dto;

import com.beansAndBite.beansAndBite.enums.Category;
import com.beansAndBite.beansAndBite.enums.ProductType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

public class ProductRequestDTO {

    @NotBlank(message = "Name is mandatory")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @Size(max = 500, message = "Product info cannot exceed 500 characters")
    private String productInfo;

    @NotBlank(message = "Product details are mandatory")
    @Size(max = 500, message = "Product details cannot exceed 500 characters")
    private String productDetails;

    @NotNull(message = "Category is mandatory")
    private Category category;

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

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    // Getters and Setters (or you can use Lombok @Data for brevity)

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductCartImage() {
        return productCartImage;
    }

    public void setProductCartImage(String productCartImage) {
        this.productCartImage = productCartImage;
    }

    public String getProductDetailsImage() {
        return productDetailsImage;
    }

    public void setProductDetailsImage(String productDetailsImage) {
        this.productDetailsImage = productDetailsImage;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}
