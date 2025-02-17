package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.dto.LoginRequest;
import com.beansAndBite.beansAndBite.dto.LoginResponse;
import com.beansAndBite.beansAndBite.dto.LoginResponseDTO;
import com.beansAndBite.beansAndBite.entity.User;
import com.beansAndBite.beansAndBite.exception.UserNotFoundException;
import com.beansAndBite.beansAndBite.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public AuthenticationService(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO signup(@RequestBody @Valid User user) {
        System.out.println("inside sign up logic");
        String userName = user.getUsername();
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        LoginRequest loginRequest = LoginRequest.builder().userName(userName).password(password).build();
        return signIn(loginRequest);
    }

    @Transactional
    public User authenticate(LoginRequest input) {
        System.out.println("input for authentication username " + input.getUserName() + " password " +input.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUserName(),
                        input.getPassword()
                )
        );
        System.out.println("finding in db");
        User user = userRepository.findByEmailIdOrMobileNumber(input.getUserName())
                .orElseThrow();

//        Hibernate.initialize(user.getCart());
//        Hibernate.initialize(user.getFavourites());
//        Hibernate.initialize(user.getOrders());
//        Hibernate.initialize(user.getGifts());

        return user;
    }

    public LoginResponseDTO signIn(LoginRequest loginRequest){
        System.out.println("authentication login request");
        User authenticatedUser = authenticate(loginRequest);
        //System.out.println("authenticated user " + authenticatedUser);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        int cartCount = userRepository.countCartItemsByUserId(authenticatedUser.getId());
        List<Long> favouriteProductIds = userRepository.findFavouriteProductIdsByUserId(authenticatedUser.getId());
        int favouriteCount = favouriteProductIds.size();

        LoginResponseDTO loginResponse = LoginResponseDTO.builder().
                token(jwtToken).
                cartCount(cartCount).
                favouriteCount(favouriteCount).
                favourites(favouriteProductIds).
                wallet(authenticatedUser.getWallet()).
                build();

//        Map<String, Object> response = new HashMap<>();
//        response.put("token", jwtToken);
//        response.put("cartCount", cartCount);
//        response.put("favouriteCount", favouriteCount);
//        response.put("favourites", favouriteProductIds);
//        response.put("wallet", authenticatedUser.getWallet());
//        System.out.println("returning response from authentication sign in");
        return loginResponse;
    }

}