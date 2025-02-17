package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.dto.CartItemDTO;
import com.beansAndBite.beansAndBite.entity.CartItem;
import com.beansAndBite.beansAndBite.util.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface CartService {
   boolean addToCart(CartItemDTO cartItemDTO);
}
