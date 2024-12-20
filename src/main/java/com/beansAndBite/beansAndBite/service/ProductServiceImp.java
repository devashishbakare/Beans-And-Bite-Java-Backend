package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.dto.ProductRequestDTO;
import com.beansAndBite.beansAndBite.entity.Product;
import com.beansAndBite.beansAndBite.exception.DataIntegrityViolationException;
import com.beansAndBite.beansAndBite.exception.ResourceNotFoundException;
import com.beansAndBite.beansAndBite.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductServiceImp implements ProductService{
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImp(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @Override
    public List<Product> addProduct(List<ProductRequestDTO> productRequests) {
        List<Product> products = new ArrayList<>();
        for (ProductRequestDTO dto : productRequests) {
            Product product = new Product();
            product.setName(dto.getName());
            product.setProductInfo(dto.getProductInfo());
            product.setProductDetails(dto.getProductDetails());
            product.setCategory(dto.getCategory());
            product.setPrice(dto.getPrice());
            product.setProductCartImage(dto.getProductCartImage());
            product.setProductDetailsImage(dto.getProductDetailsImage());
            product.setProductType(dto.getProductType());
            products.add(product);
        }
        try{
            return productRepository.saveAll(products);
        }catch(DataIntegrityViolationException exception){
            throw new DataIntegrityViolationException("input data failed to map with entity " + exception.getMessage());
        }
    }

    @Override
    public Product getProductInfo(long id) {
        try{
            return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        }catch(ResourceNotFoundException ex){
            throw new ResourceNotFoundException("product not fond with id " + id + ex.getMessage());
        }
    }
}
