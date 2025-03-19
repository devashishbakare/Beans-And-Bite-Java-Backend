package com.beansAndBite.beansAndBite.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class CreateOrderResponse {
    private Long orderId;
    private String paymentMethod;
}
