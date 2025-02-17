package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.entity.Product;
import com.beansAndBite.beansAndBite.entity.User;
import com.beansAndBite.beansAndBite.exception.ProductNotFoundException;
import com.beansAndBite.beansAndBite.exception.UserNotFoundException;
import com.beansAndBite.beansAndBite.repository.ProductRepository;
import com.beansAndBite.beansAndBite.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FavoriteServiceImp implements FavoriteService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public boolean addToFavorites(List<Long> favorites){
        log.info("Receiving request in implementer");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        long userId = authenticatedUser.getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
        List<Product> products = productRepository.findAllById(favorites);
        if(products.size() != favorites.size()){
            throw new ProductNotFoundException("product not found");
        }
        user.getFavourites().addAll(products);
        userRepository.save(user);
        log.info("sending response after saving");
        return true;
    }

    public List<Product> fetchUserFavoritesProduct(List<Long> favourites){
        List<Product> userFavouritesProduct = productRepository.findAllById(favourites);
        if(userFavouritesProduct.size() != favourites.size()){
            throw new ProductNotFoundException("Product Not Found");
        }
        return userFavouritesProduct;
    }

}
