package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.dto.CreateOrderResponse;
import com.beansAndBite.beansAndBite.dto.OrderHistoryCollection;
import com.beansAndBite.beansAndBite.dto.PlaceOrderDTO;
import com.beansAndBite.beansAndBite.entity.Order;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface OrderService {
    public CreateOrderResponse createOrder(PlaceOrderDTO placeOrderDTO);
    public OrderHistoryCollection orderHistory(int page, int limit);

}