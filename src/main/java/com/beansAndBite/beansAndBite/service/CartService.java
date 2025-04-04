package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.dto.CartItemDTO;
import com.beansAndBite.beansAndBite.dto.FetchCartProductDTO;
import com.beansAndBite.beansAndBite.entity.CartItem;
import com.beansAndBite.beansAndBite.util.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {
   boolean addToCart(CartItemDTO cartItemDTO);

   List<FetchCartProductDTO> fetchAllCartItem();

   void updateCartProduct(CartItemDTO cartItemDTO);

   void deleteCartItem(Long cartId);
}