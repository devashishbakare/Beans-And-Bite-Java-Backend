package com.beansAndBite.beansAndBite.dto;

import com.beansAndBite.beansAndBite.enums.PaymentMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
@Getter
@Setter
@Builder
public class OrderHistoryResponse {
    private long orderId;
    private long userId;
    private List<CartItemData>products = new ArrayList<>();
    private String takeAwayFrom;
    private double amount;
    private PaymentMethod paymentMethod;
    private String additionalMessage;
}
