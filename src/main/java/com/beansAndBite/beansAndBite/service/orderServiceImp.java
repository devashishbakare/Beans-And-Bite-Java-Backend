package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.dto.*;
import com.beansAndBite.beansAndBite.entity.CartItem;
import com.beansAndBite.beansAndBite.entity.Order;
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
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
        //log.info("reached at create order");
        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //log.info("fetched user {}", user.getName());
        List<CartItem> cartItems = new ArrayList<>();
        double totalAmount = 0;
        //log.info("fetching cartItem from cart");
        for(long cartCustomizationId : placeOrderDTO.getProducts()){
            CartItem cartItem = cartItemRepository.findById(cartCustomizationId).orElseThrow(() -> new ResourceNotFoundException("cart item not found"));
            cartItems.add(cartItem);
            totalAmount += cartItem.getAmount();
        }
        //log.info("product size {}", products.size());
        //adding 5% tax here
        totalAmount += (totalAmount / 100) * 5;
        totalAmount = Math.round(totalAmount * 100.0) / 100.0;
        log.info("total amount {} ", totalAmount);
        if(placeOrderDTO.getAmount() != totalAmount){
            throw new AmountMismatchException("input amount and total amount is not matching");
        }
        PaymentMethod paymentMethod = EnumUtil.convertToEnum(PaymentMethod.class, placeOrderDTO.getPaymentMethod());
        log.info("converted to enum {} ", paymentMethod);
        Order order = Order.builder().user(user).cartItems(cartItems).takeAwayFrom(placeOrderDTO.getTakeAwayFrom()).amount(totalAmount).paymentMethod(paymentMethod).additionalMessage(placeOrderDTO.getAdditionalMessage()).build();
        orderRepository.save(order);
        log.info("order has been created {}", order.getId());
        if(placeOrderDTO.getPaymentMethod().toLowerCase().equals("wallet")){
            if(user.getWallet() < totalAmount){
                throw new InsufficientBalanceException("wallet money is not enough");
            }
            //Hibernate.initialize(user.getCart());
            log.info("inside a updating user info");
            user.setCart(new ArrayList<>());
            user.setWallet(user.getWallet()-totalAmount);
            userRepository.save(user);
            log.info("User cart cleared successfully!");
            return new CreateOrderResponseForWallet(order.getId(), placeOrderDTO.getPaymentMethod(), user.getWallet());
        }else{
           return new CreateOrderResponseForPaymentGateway(order.getId(), placeOrderDTO.getPaymentMethod());
        }
    }

    @Transactional
    public OrderHistoryCollection orderHistory(int page, int limit){
        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = user.getId();
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by("createdAt").descending());
        Page<Order> ordersData = orderRepository.findOrderByUserId(userId, pageable);
        List<OrderHistoryResponse> orders = new ArrayList<>();
        for(Order order : ordersData.getContent()){

            List<CartItemData> cartItemDataList = new ArrayList<>();

            for(CartItem cartItem : order.getCartItems()){
                CartItemData cartItemData = CartItemData.builder()
                .userId(cartItem.getUser().getId()).
                        productId(cartItem.getProduct()).
                        amount(cartItem.getAmount()).
                        size(cartItem.getSize()).
                        milk(cartItem.getMilk()).
                        espresso(cartItem.getEspresso()).
                        temperature(cartItem.getTemperature()).
                        whippedTopping(cartItem.getWhippedTopping()).
                        syrupAndSaucesInfo(cartItem.getSyrupAndSaucesInfo()).
                        createdAt(cartItem.getCreatedAt()).
                        updatedAt(cartItem.getUpdatedAt()).
                        build();
                cartItemDataList.add(cartItemData);

            }
            OrderHistoryResponse orderHistoryResponse = OrderHistoryResponse.builder()
                    ._id(order.getId())
                    .orderId(order.getId())
                    .userId(order.getUser().getId())
                    .products(cartItemDataList)
                    .takeAwayFrom(order.getTakeAwayFrom())
                    .amount(order.getAmount())
                    .paymentMethod(order.getPaymentMethod())
                    .additionalMessage(order.getAdditionalMessage())
                    .createdAt(order.getCreatedAt())
                    .updatedAt(order.getUpdatedAt()).
                    build();
            orders.add(orderHistoryResponse);
        }

        long totalOrder = ordersData.getTotalElements();
        return new OrderHistoryCollection(orders, totalOrder);
    }

}