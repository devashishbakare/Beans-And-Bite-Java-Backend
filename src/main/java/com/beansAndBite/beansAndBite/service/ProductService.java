package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.dto.ProductRequestDTO;
import com.beansAndBite.beansAndBite.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface ProductService {
    List<Product> addProduct(List<ProductRequestDTO> product);
    Product getProductInfo(long id);

    Page<Product> getSearchResult(String searchQuery, int page);

}
