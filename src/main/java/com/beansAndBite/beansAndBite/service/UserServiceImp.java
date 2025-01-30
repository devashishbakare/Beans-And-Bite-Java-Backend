package com.beansAndBite.beansAndBite.service;
import com.beansAndBite.beansAndBite.dto.LoginRequest;
import com.beansAndBite.beansAndBite.entity.User;
import com.beansAndBite.beansAndBite.exception.AuthenticationException;
import com.beansAndBite.beansAndBite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public UserServiceImp(UserRepository userRepository,
                          AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder){
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User signUpUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User authenticate(LoginRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUserName(),
                        input.getPassword()
                )
        );
        return userRepository.findByEmailIdOrMobileNumber(input.getUserName()).orElseThrow();
    }

    @Override
    public Map<String, Object> signInUser(LoginRequest loginRequest) {
        return null;
    }
}
