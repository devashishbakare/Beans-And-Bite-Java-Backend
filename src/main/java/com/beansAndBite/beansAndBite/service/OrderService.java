package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.dto.CreateOrderResponse;
import com.beansAndBite.beansAndBite.dto.PlaceOrderDTO;

public interface OrderService {
    public CreateOrderResponse createOrder(PlaceOrderDTO placeOrderDTO);
}