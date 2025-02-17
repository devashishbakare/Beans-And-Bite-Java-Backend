package com.beansAndBite.beansAndBite.dto;

import com.beansAndBite.beansAndBite.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
public class FetchCartProductDTO {
    private Long _id;
    private Long userId;
    private Product productId;
    private double amount;
    private String size;
    private String milk;
    private String espresso;
    private String temperature;
    private String whippedTopping;
    private List<SyrupAndSauceResponse> SyrupAndSauce = new ArrayList<>();
    @Setter
    @Getter
    @AllArgsConstructor
    public static class SyrupAndSauceResponse {
        private String type;
        private Integer quantity;
    }
}
