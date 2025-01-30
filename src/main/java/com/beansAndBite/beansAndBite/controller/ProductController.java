package com.beansAndBite.beansAndBite.controller;
import java.time.LocalDateTime;
import java.util.*;

import com.beansAndBite.beansAndBite.dto.ProductRequestDTO;
import com.beansAndBite.beansAndBite.entity.Product;

import com.beansAndBite.beansAndBite.enums.Category;
import com.beansAndBite.beansAndBite.exception.CategoryNotFoundException;
import com.beansAndBite.beansAndBite.exception.ErrorResponse;
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

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<?> getProductByCategory(@PathVariable String categoryName){
        try{
            Category category = Category.valueOf(categoryName);
            //System.out.println(category);
            List<Product> products = productService.productByCategory(category);
            Map<String, Object> storeResponse = Map.of("message", "product by category",
                    "data" , products);
            return ResponseEntity.status(HttpStatus.OK).body(storeResponse);
        }catch(Exception ex){
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Category not found", LocalDateTime.now().toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/homepage")
    public ResponseEntity<?> fetchProductsForHomepage(){
        try{
            List<Category> categories = Arrays.asList(Category.Bestseller, Category.Drinks);
            List<Product> products = productService.getProductForHomepage(categories);
            return ResponseEntity.status(HttpStatus.OK).body(products);
        }catch(Exception ex){
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong " + ex.getMessage(), LocalDateTime.now().toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
