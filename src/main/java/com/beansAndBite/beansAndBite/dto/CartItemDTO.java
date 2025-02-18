package com.beansAndBite.beansAndBite.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class CartItemDTO {

    private Long cartId; // Nullable, so no validation

    @NotBlank(message = "Espresso selection is required")
    private String espresso;

    @NotBlank(message = "Milk selection is required")
    private String milk;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive value")
    private Double price;

    @NotBlank(message = "Product ID is required")
    private String productId;

    @NotBlank(message = "Size selection is required")
    private String size;

    @NotNull(message = "Syrup and sauces list cannot be null")
    @Size(min = 1, message = "At least one syrup or sauce must be selected")
    private List<SyrupAndSauceDTO> syrupAndSauces;

    @NotBlank(message = "Temperature selection is required")
    private String temperature;

    @NotBlank(message = "Whipped topping selection is required")
    private String whippedTopping;

    @Getter
    @Setter
    public static class SyrupAndSauceDTO {

        @NotBlank(message = "Type is required")
        private String type;

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        private Integer quantity;

    }
}
