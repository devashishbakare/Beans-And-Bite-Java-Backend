package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.dto.ProductRequestDTO;
import com.beansAndBite.beansAndBite.entity.Product;
import com.beansAndBite.beansAndBite.exception.DataIntegrityViolationException;
import com.beansAndBite.beansAndBite.exception.ResourceNotFoundException;
import com.beansAndBite.beansAndBite.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;


@Service
public class ProductServiceImp implements ProductService{
    private ProductRepository productRepository;
    @Autowired
    private EntityManager entityManager;
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

    public Page<Product> getSearchResult(String searchQuery, int page){
        String searchKeywords[] = searchQuery.split(" ");
        Pageable pageable = PageRequest.of(page-1, 5);

        StringBuilder customQuery = new StringBuilder("select p from Product p where ");

        List<String> subQueries = new ArrayList<>();

        for(int index = 0; index < searchKeywords.length; index++){

            String searchKeywordParam = "keyword" + index;
            String subQuery = "(LOWER(p.name) LIKE LOWER(CONCAT('%', :" + searchKeywordParam + ", '%')) " +
                    "OR LOWER(p.category) LIKE LOWER(CONCAT('%', :" + searchKeywordParam + ", '%')) " +
                    "OR LOWER(p.productType) LIKE LOWER(CONCAT('%', :" + searchKeywordParam + ", '%')))";

            subQueries.add(subQuery);
        }

        customQuery.append(String.join(" OR ", subQueries));

        TypedQuery<Product> query =  entityManager.createQuery(customQuery.toString(), Product.class);

        for(int i = 0; i < searchKeywords.length; i++){
            String keyword = searchKeywords[i];
            query.setParameter("keyword" + i, keyword);
        }

        query.setFirstResult((page-1) * 5);
        query.setMaxResults(5);

        List<Product> result = query.getResultList();
        //till now we have the result, to know user better we need to send other information as well

        String updatedQuery = customQuery.toString().replace("select p", "select count(p)");
        TypedQuery<Long> countQuery = entityManager.createQuery(updatedQuery, Long.class);

        for(int i = 0; i < searchKeywords.length; i++){
            countQuery.setParameter("keyword" + i, searchKeywords[i]);
        }

        long totalResult = countQuery.getSingleResult();
        return new PageImpl<>(result, pageable, totalResult);
    }

}
