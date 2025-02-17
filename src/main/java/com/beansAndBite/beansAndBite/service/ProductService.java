package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.dto.ProductRequestDTO;
import com.beansAndBite.beansAndBite.entity.Product;
import com.beansAndBite.beansAndBite.enums.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface ProductService {
    List<Product> addProduct(List<ProductRequestDTO> product);
    Product getProductInfo(long id);

    Page<Product> getSearchResult(String searchQuery, int page);

    List<Product> productByCategory(Category category);
    List<Product> getProductForHomepage(List<Category> categories);
    List<Product> updateAllCategory();
    List<Product> showAllProduct();
}
