package com.beansAndBite.beansAndBite.controller;

import com.beansAndBite.beansAndBite.dto.CartItemDTO;
import com.beansAndBite.beansAndBite.entity.CartItem;
import com.beansAndBite.beansAndBite.entity.Product;
import com.beansAndBite.beansAndBite.enums.Category;
import com.beansAndBite.beansAndBite.service.CartService;
import com.beansAndBite.beansAndBite.util.BaseResponse;
import com.beansAndBite.beansAndBite.util.ErrorInfo;
import com.beansAndBite.beansAndBite.util.Response;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addToCart(@RequestBody @Valid CartItemDTO cartItemDTO){
           boolean responseFlag = cartService.addToCart(cartItemDTO);
           if(responseFlag){
               Response<CartItem> response = new Response<>("item add to cart successfully", null);
               return ResponseEntity.status(HttpStatus.OK).body(response);
           }else{
               ErrorInfo errorResponse = new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", LocalDateTime.now().toString());
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
           }
    }
}
