package com.beansAndBite.beansAndBite.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderResponseForWallet extends CreateOrderResponse{

    private double wallet;

    public CreateOrderResponseForWallet(Long orderId, String paymentMethod, double wallet) {
        super(orderId, paymentMethod);
        this.wallet = wallet;
    }
}
