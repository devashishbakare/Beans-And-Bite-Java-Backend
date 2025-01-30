package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.entity.User;
import com.beansAndBite.beansAndBite.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIdOrMobileNumber(username).orElseThrow();
        //System.out.println("loaded user in custom user details " +user);
        return user;
    }
}
