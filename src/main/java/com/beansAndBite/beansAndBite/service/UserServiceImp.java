package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.dto.LoginRequest;
import com.beansAndBite.beansAndBite.entity.User;
import com.beansAndBite.beansAndBite.exception.ResourceNotFoundException;
import com.beansAndBite.beansAndBite.exception.UserNotFoundException;
import com.beansAndBite.beansAndBite.repository.UserRepository;
import com.beansAndBite.beansAndBite.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public String signUpUser(User user) {

        String email = user.getEmail();
        String mobileNumber = user.getMobileNumber();
        String password = user.getPassword();

        if(userRepository.findByEmailOrMobileNumber(email, mobileNumber).isPresent()){
            throw new RuntimeException("email or mobile number is already exists");
        }

        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);

        User savedUser = userRepository.save(user);
        return jwtUtil.generateToken(email, savedUser.getId());

    }

    @Override
    public String signInUser(LoginRequest loginRequest) {

        String userName = loginRequest.getUserName();
        String password = loginRequest.getPassword();

        /*
        User user = userRepository.findByEmail(userName);

        if(user == null){
            user = userRepository.findByMobileNumber(userName);
        }
         */

        User user = userRepository.findByEmailIdOrMobileNumber(userName);

        if(user == null || passwordEncoder.matches(password, user.getPassword()) == false){
            throw new RuntimeException("Username or password is not correct");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getId());
    }
}
