package com.beansAndBite.beansAndBite.repository;

import com.beansAndBite.beansAndBite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<Object> findByEmailOrMobileNumber(String email, String mobileNumber);
    User findByEmail(String email);
    User findByMobileNumber(String mobileNumber);

    @Query("select u from User u where u.email = :userName OR u.mobileNumber = :userName")
    User findByEmailIdOrMobileNumber(@Param("userName") String userName);
}
