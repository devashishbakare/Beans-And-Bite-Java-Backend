package com.beansAndBite.beansAndBite.repository;

import com.beansAndBite.beansAndBite.entity.GiftDetails;
import com.beansAndBite.beansAndBite.entity.GiftStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GiftDetailsRepository extends JpaRepository<GiftDetails, Long> {
    @Query("SELECT gs FROM GiftStatus gs " +
            "JOIN FETCH gs.giftDetails " +
            "WHERE gs.user.id = :userId " +
            "ORDER BY gs.createdAt DESC")
    Page<GiftStatus> findGiftDetailsFromUserId(@Param("userId") Long userId, Pageable pageable);

}
