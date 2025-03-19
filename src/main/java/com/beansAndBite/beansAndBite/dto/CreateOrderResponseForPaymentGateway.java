package com.beansAndBite.beansAndBite.dto;

public class CreateOrderResponseForPaymentGateway extends CreateOrderResponse{
    public CreateOrderResponseForPaymentGateway(Long orderId, String paymentMethod) {
        super(orderId, paymentMethod);
    }
}
