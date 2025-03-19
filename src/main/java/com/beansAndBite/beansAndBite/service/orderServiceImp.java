package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.dto.*;
import com.beansAndBite.beansAndBite.entity.CartItem;
import com.beansAndBite.beansAndBite.entity.Order;
import com.beansAndBite.beansAndBite.entity.Product;
import com.beansAndBite.beansAndBite.entity.User;
import com.beansAndBite.beansAndBite.enums.PaymentMethod;
import com.beansAndBite.beansAndBite.exception.AmountMismatchException;
import com.beansAndBite.beansAndBite.exception.InsufficientBalanceException;
import com.beansAndBite.beansAndBite.exception.ResourceNotFoundException;
import com.beansAndBite.beansAndBite.repository.CartItemRepository;
import com.beansAndBite.beansAndBite.repository.OrderRepository;
import com.beansAndBite.beansAndBite.repository.UserRepository;
import com.beansAndBite.beansAndBite.util.EnumUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class orderServiceImp implements OrderService{

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Transactional
    public CreateOrderResponse createOrder(PlaceOrderDTO placeOrderDTO){
        log.info("reached at create order");
        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("fetched user {}", user.getName());
        List<Product> products = new ArrayList<>();
        double totalAmount = 0;
        log.info("fetching cartItem from cart");
        for(long cartCustomizationId : placeOrderDTO.getProducts()){
            CartItem cartItem = cartItemRepository.findById(cartCustomizationId).orElseThrow(() -> new ResourceNotFoundException("cart item not found"));
            totalAmount += cartItem.getAmount();
            products.add(cartItem.getProduct());
        }
        log.info("product size {}", products.size());
        //adding 5% tax here
        totalAmount += (totalAmount / 100) * 5;
        totalAmount = Math.round(totalAmount * 100.0) / 100.0;
        log.info("total amount {} ", totalAmount);
        if(placeOrderDTO.getAmount() != totalAmount){
            throw new AmountMismatchException("input amount and total amount is not matching");
        }
        PaymentMethod paymentMethod = EnumUtil.convertToEnum(PaymentMethod.class, placeOrderDTO.getPaymentMethod());
        log.info("converted to enum {} ", paymentMethod);
        Order order = Order.builder().user(user).products(products).takeAwayFrom(placeOrderDTO.getTakeAwayFrom()).amount(totalAmount).paymentMethod(paymentMethod).additionalMessage(placeOrderDTO.getAdditionalMessage()).build();
        orderRepository.save(order);
        log.info("order has been created");
        if(placeOrderDTO.getPaymentMethod().toLowerCase().equals("wallet")){
            if(user.getWallet() < totalAmount){
                throw new InsufficientBalanceException("wallet money is not enough");
            }
            log.info("setting user 63");
            user.setCart(new ArrayList<>());
            user.setWallet(user.getWallet()-totalAmount);
            userRepository.save(user);
            log.info("user updates has been saved");
            return new CreateOrderResponseForWallet(order.getId(), placeOrderDTO.getPaymentMethod(), user.getWallet());
        }else{
           return new CreateOrderResponseForPaymentGateway(order.getId(), placeOrderDTO.getPaymentMethod());
        }
    }

}