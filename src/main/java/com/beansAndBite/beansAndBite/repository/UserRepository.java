package com.beansAndBite.beansAndBite.repository;

import com.beansAndBite.beansAndBite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //User findByEmailOrMobileNumber(String email, String mobileNumber);
    Optional<User> findByEmail(String email);

    Optional<User> findByMobileNumber(String mobileNumber);

    @Query("select u from User u where u.email = :userName OR u.mobileNumber = :userName")
    Optional<User> findByEmailIdOrMobileNumber(@Param("userName") String userName);
}
