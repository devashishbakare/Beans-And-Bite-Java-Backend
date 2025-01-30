package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.dto.LoginRequest;
import com.beansAndBite.beansAndBite.entity.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    User signUpUser(User user);
    Map<String, Object> signInUser(LoginRequest loginRequest);



}
