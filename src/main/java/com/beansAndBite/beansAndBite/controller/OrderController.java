package com.beansAndBite.beansAndBite.controller;

import com.beansAndBite.beansAndBite.dto.CreateOrderResponse;
import com.beansAndBite.beansAndBite.dto.OrderHistoryCollection;
import com.beansAndBite.beansAndBite.dto.PlaceOrderDTO;
import com.beansAndBite.beansAndBite.entity.Order;
import com.beansAndBite.beansAndBite.service.OrderService;
import com.beansAndBite.beansAndBite.util.BaseResponse;
import com.beansAndBite.beansAndBite.util.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController{

   public OrderService orderService;
   public OrderController(OrderService orderService){
       this.orderService = orderService;
   }

    @PostMapping("/createOrder")
    public  ResponseEntity<BaseResponse> createOrder(@RequestBody PlaceOrderDTO placeOrderDTO){
        CreateOrderResponse orderDetails = orderService.createOrder(placeOrderDTO);
        Response<CreateOrderResponse> response = new Response<>("order has been created", orderDetails);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/history")
    public ResponseEntity<BaseResponse> fetchOrderHistory(@RequestParam int page, @RequestParam int limit){
        Response<OrderHistoryCollection> response = new Response<>("Order History", orderService.orderHistory(page, limit));
       return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}