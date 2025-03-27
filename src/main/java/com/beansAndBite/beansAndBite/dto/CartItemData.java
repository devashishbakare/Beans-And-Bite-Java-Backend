package com.beansAndBite.beansAndBite.dto;

import com.beansAndBite.beansAndBite.entity.Product;
import com.beansAndBite.beansAndBite.entity.SyrupAndSaucesInfo;
import com.beansAndBite.beansAndBite.enums.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Builder
public class CartItemData {
    private long userId;
    private Product productId;
    private double amount;
    private Size size;
    private Milk milk;
    private Espresso espresso;
    private Temperature temperature;
    private WhippedTopping whippedTopping;
    private List<SyrupAndSaucesInfo> syrupAndSaucesInfo = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
