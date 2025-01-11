package com.beansAndBite.beansAndBite.repository;
import com.beansAndBite.beansAndBite.entity.Product;
import com.beansAndBite.beansAndBite.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);

    //Query(select p from product p where p.category in :categories) @param("categories") List<Category> categoryList
    List<Product> findByCategoryIn(List<Category> categories);
}
