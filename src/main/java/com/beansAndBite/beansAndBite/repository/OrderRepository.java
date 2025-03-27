package com.beansAndBite.beansAndBite.repository;

import com.beansAndBite.beansAndBite.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId ORDER BY o.createdAt DESC")
    Page<Order> findOrderByUserId(@Param("userId") Long userId, Pageable pageable);

}
