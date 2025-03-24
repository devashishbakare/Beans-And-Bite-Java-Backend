package com.beansAndBite.beansAndBite.service;
import com.beansAndBite.beansAndBite.dto.LoginRequest;
import com.beansAndBite.beansAndBite.entity.User;
import com.beansAndBite.beansAndBite.exception.AuthenticationException;
import com.beansAndBite.beansAndBite.repository.UserRepository;
import com.beansAndBite.beansAndBite.util.BaseResponse;
import com.beansAndBite.beansAndBite.util.ErrorInfo;
import com.beansAndBite.beansAndBite.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
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

    @Override
    @Transactional
   public ResponseEntity<BaseResponse> fetchUserDetails(){
        try{
            System.out.println("fetching user details");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();
            User user = userRepository.findByEmail(currentUser.getEmail()).orElseThrow();

            Hibernate.initialize(user.getCart());
//            Hibernate.initialize(user.getFavourites());
//            Hibernate.initialize(user.getOrders());
//            Hibernate.initialize(user.getGifts());

            Response<User> response = new Response<>("user details", user);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(Exception ex){
            ErrorInfo errorResponse = new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong " + ex.getMessage(), LocalDateTime.now().toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
   }

    public int getCount(){
        log.info("reaching here");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        User user = userRepository.findByEmail(currentUser.getEmail()).orElseThrow();
        return user.getCart().size();
    }

}


