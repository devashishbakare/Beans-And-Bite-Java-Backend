package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.dto.LoginRequest;
import com.beansAndBite.beansAndBite.entity.User;


public interface UserService {
    String signUpUser(User user);
    String signInUser(LoginRequest loginRequest);
}
