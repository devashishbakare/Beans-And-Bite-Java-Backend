package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.dto.CartItemDTO;
import com.beansAndBite.beansAndBite.dto.FetchCartProductDTO;
import com.beansAndBite.beansAndBite.entity.CartItem;
import com.beansAndBite.beansAndBite.entity.Product;
import com.beansAndBite.beansAndBite.entity.SyrupAndSaucesInfo;
import com.beansAndBite.beansAndBite.entity.User;
import com.beansAndBite.beansAndBite.enums.*;
import com.beansAndBite.beansAndBite.exception.InvalidEnumValueException;
import com.beansAndBite.beansAndBite.exception.ResourceNotFoundException;
import com.beansAndBite.beansAndBite.exception.UserNotFoundException;
import com.beansAndBite.beansAndBite.repository.CartItemRepository;
import com.beansAndBite.beansAndBite.repository.ProductRepository;
import com.beansAndBite.beansAndBite.repository.UserRepository;
import com.beansAndBite.beansAndBite.util.BaseResponse;
import com.beansAndBite.beansAndBite.util.EnumUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class CartServiceImp implements CartService{

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    UserRepository userRepository;


    @Transactional
    @Override
    public boolean addToCart(CartItemDTO cartItemDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        Long userId = authenticatedUser.getId();

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Product product = productRepository.findById(Long.valueOf(cartItemDTO.getProductId()))
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setAmount(cartItemDTO.getPrice());

        validateAndSetEnums(cartItem, cartItemDTO);

        List<SyrupAndSaucesInfo> storeSyrupAndSaucesInfo = new ArrayList<>();
        for (CartItemDTO.SyrupAndSauceDTO syrupInfo : cartItemDTO.getSyrupAndSauces()) {
            SyrupAndSauce syrupAndSauce = EnumUtil.convertToEnum(SyrupAndSauce.class, syrupInfo.getType());
            SyrupAndSaucesInfo syrupAndSaucesInfo = SyrupAndSaucesInfo.builder()
                    .syrupAndSauce(syrupAndSauce)
                    .quantity(syrupInfo.getQuantity())
                    .build();
            storeSyrupAndSaucesInfo.add(syrupAndSaucesInfo);
        }

        cartItem.setSyrupAndSaucesInfo(storeSyrupAndSaucesInfo);

        cartItem = cartItemRepository.save(cartItem);
        user.getCart().add(cartItem.getId());
        userRepository.save(user);
        log.info("CartItem successfully added to user with ID: {}", userId);
        return true;
    }

    private void validateAndSetEnums(CartItem cartItem, CartItemDTO cartItemDTO) {
        try {
            cartItem.setSize(EnumUtil.convertToEnum(Size.class, cartItemDTO.getSize()));
            cartItem.setMilk(EnumUtil.convertToEnum(Milk.class, cartItemDTO.getMilk()));
            cartItem.setEspresso(EnumUtil.convertToEnum(Espresso.class, cartItemDTO.getEspresso()));
            cartItem.setTemperature(EnumUtil.convertToEnum(Temperature.class, cartItemDTO.getTemperature()));
            cartItem.setWhippedTopping(EnumUtil.convertToEnum(WhippedTopping.class, cartItemDTO.getWhippedTopping()));
        } catch (IllegalArgumentException e) {
            log.error("Invalid enum value provided: {}", e.getMessage());
            throw new InvalidEnumValueException("Invalid enum value provided");
        }
    }


    @Transactional
    @Override
    public List<FetchCartProductDTO> fetchAllCartItem(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        User user = userRepository.findById(authenticatedUser.getId()).orElseThrow(() -> new UserNotFoundException("user not found"));
        List<CartItem> cartItems = new ArrayList<>();
        for(long cartId : user.getCart()){
            CartItem cartItem = cartItemRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("cart item not found in db"));
            cartItems.add(cartItem);
        }
        //log.info("cart size {}", cartItems.size());
        List<FetchCartProductDTO> storeCartProduct = new ArrayList<>();
        int counter = 0;
        for(CartItem cartItem : cartItems){
            //log.info("cart item syrup info has been initialize {}" ,cartItem.getSyrupAndSaucesInfo().size());
            List<FetchCartProductDTO.SyrupAndSauceResponse> storeSyrupInfo = new ArrayList<>();
            for(SyrupAndSaucesInfo syrupAndSaucesInfo : cartItem.getSyrupAndSaucesInfo()){
                FetchCartProductDTO.SyrupAndSauceResponse info = new FetchCartProductDTO.SyrupAndSauceResponse(EnumUtil.convertEnumToString(syrupAndSaucesInfo.getSyrupAndSauce()) ,syrupAndSaucesInfo.getQuantity());
                storeSyrupInfo.add(info);
            }

            FetchCartProductDTO fetchCartProductDTO = FetchCartProductDTO.builder().
                    _id(cartItem.getId()).
                    userId(user.getId()).
                    productId(cartItem.getProduct()).
                    amount(cartItem.getAmount()).
                    size(EnumUtil.convertEnumToString(cartItem.getSize())).
                    milk(EnumUtil.convertEnumToString(cartItem.getMilk())).
                    espresso(EnumUtil.convertEnumToString(cartItem.getEspresso())).
                    temperature(EnumUtil.convertEnumToString(cartItem.getTemperature())).
                    whippedTopping(EnumUtil.convertEnumToString(cartItem.getWhippedTopping())).
                    SyrupAndSauce(storeSyrupInfo).build();
            //log.info("processing cartItem {}", ++counter);
            storeCartProduct.add(fetchCartProductDTO);
        }
        //log.info("total store cart product size {}", storeCartProduct.size());
        //log.info("sample data {}", storeCartProduct.get(0).getProductId().getName());
        return storeCartProduct;
    }

    @Transactional
    @Override
    public void updateCartProduct(CartItemDTO cartItemDTO){

        long cartId = cartItemDTO.getCartId();
        CartItem cartItem = cartItemRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("cart item not found"));
        validateAndSetEnums(cartItem, cartItemDTO);
        cartItem.setAmount(cartItemDTO.getPrice());
        List<SyrupAndSaucesInfo> storeSyrupAndSaucesInfo = new ArrayList<>();
        for (CartItemDTO.SyrupAndSauceDTO syrupInfo : cartItemDTO.getSyrupAndSauces()) {
            SyrupAndSauce syrupAndSauce = EnumUtil.convertToEnum(SyrupAndSauce.class, syrupInfo.getType());
            SyrupAndSaucesInfo syrupAndSaucesInfo = SyrupAndSaucesInfo.builder()
                    .syrupAndSauce(syrupAndSauce)
                    .quantity(syrupInfo.getQuantity())
                    .build();
            storeSyrupAndSaucesInfo.add(syrupAndSaucesInfo);
        }
        cartItem.setSyrupAndSaucesInfo(storeSyrupAndSaucesInfo);
        cartItemRepository.save(cartItem);
    }

    @Transactional
    @Override
    public void deleteCartItem(Long cartId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        CartItem cartItem = cartItemRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("cart item not found"));
        User user = cartItem.getUser();

        if(!authenticatedUser.getId().equals(user.getId())){
            throw new RuntimeException("Un-Authorize access: cart item is not own by input request user");
        }
        // here deleting the cart item will also result into deleting from user because there is one to many and many to one relationship is there so there is no need of explicitly deleting cart item from user
        user.getCart().remove(cartId);
        userRepository.save(user);
        cartItemRepository.delete(cartItem);
    }

}




