package com.beansAndBite.beansAndBite.controller;
import java.util.*;

import com.beansAndBite.beansAndBite.dto.ProductRequestDTO;
import com.beansAndBite.beansAndBite.entity.Product;

import com.beansAndBite.beansAndBite.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<List<Product>> addProduct(@RequestBody @Valid List<ProductRequestDTO> ProductRequestDTO){
        List<Product> addedProducts = productService.addProduct(ProductRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProducts);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Product> getProductInfo(@PathVariable Long id){
        Product product = productService.getProductInfo(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(@RequestParam String searchQuery, @RequestParam int page){
        Page<Product> searchResult = productService.getSearchResult(searchQuery, page);

        Map<String, Object> response = Map.of(
          "message" , "search result",
                "data", searchResult.getContent(),
                "total pages" , searchResult.getTotalPages(),
                "total product" , searchResult.getTotalElements(),
                "data loaded for page no: ", searchResult.getNumber()+1
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
