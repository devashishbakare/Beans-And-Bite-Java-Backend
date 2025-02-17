package com.beansAndBite.beansAndBite.controller;
import java.time.LocalDateTime;
import java.util.*;

import com.beansAndBite.beansAndBite.dto.ProductRequestDTO;
import com.beansAndBite.beansAndBite.entity.Product;

import com.beansAndBite.beansAndBite.enums.Category;
import com.beansAndBite.beansAndBite.exception.CategoryNotFoundException;
import com.beansAndBite.beansAndBite.exception.ErrorResponse;
import com.beansAndBite.beansAndBite.service.ProductService;
import com.beansAndBite.beansAndBite.util.BaseResponse;
import com.beansAndBite.beansAndBite.util.ErrorInfo;
import com.beansAndBite.beansAndBite.util.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<List<Product>> addProduct(@RequestBody @Valid List<ProductRequestDTO> ProductRequestDTO){
        List<Product> addedProducts = productService.addProduct(ProductRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProducts);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response<Product>> getProductInfo(@PathVariable Long id){
        Product product = productService.getProductInfo(id);
        System.out.println("this is product " + product);
        Response<Product> response = new Response<>("Product info", product);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(@RequestParam String searchQuery, @RequestParam int page){
        Page<Product> searchResult = productService.getSearchResult(searchQuery, page);

        Map<String, Object> response = Map.of(
          "message" , "search result",
                "data", searchResult.getContent(),
                "totalPages" , searchResult.getTotalPages(),
                "totalProduct" , searchResult.getTotalElements(),
                "currentPage", searchResult.getNumber()+1
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
    public ResponseEntity<BaseResponse> fetchProductsForHomepage(){
        try{
            List<Category> categories = Arrays.asList(Category.Bestseller, Category.Drinks);
            List<Product> products = productService.getProductForHomepage(categories);
            Response<List<Product>> response = new Response<>("product for homepage", products);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(Exception ex){
            ErrorInfo errorResponse = new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong " + ex.getMessage(), LocalDateTime.now().toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/update")
    public ResponseEntity<List<Product>> updateProduct(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateAllCategory());
    }

    @GetMapping("/show")
    public ResponseEntity<List<Product>> showAllProduct(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.showAllProduct());
    }


}
