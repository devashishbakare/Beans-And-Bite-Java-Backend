package com.beansAndBite.beansAndBite.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderHistoryCollection {
    List<OrderHistoryResponse> orders;
    long totalOrder;
}
