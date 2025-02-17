package com.beansAndBite.beansAndBite.service;
import com.beansAndBite.beansAndBite.entity.Product;

import java.util.List;
public interface FavoriteService{
    boolean addToFavorites(List<Long> favorites);
    List<Product> fetchUserFavoritesProduct(List<Long> favourites);
}
