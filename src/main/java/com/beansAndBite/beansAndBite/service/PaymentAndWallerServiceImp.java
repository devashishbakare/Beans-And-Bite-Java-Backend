package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.entity.User;
import com.beansAndBite.beansAndBite.exception.ResourceNotFoundException;
import com.beansAndBite.beansAndBite.repository.UserRepository;
import org.hibernate.boot.model.process.internal.UserTypeResolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PaymentAndWallerServiceImp implements PaymentAndWallerService{

    @Autowired
    private UserRepository userRepository;

    public double addToWaller(double amount){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        Long userId = authenticatedUser.getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setWallet(user.getWallet() + amount);
        userRepository.save(user);
        return user.getWallet();
    }

}
