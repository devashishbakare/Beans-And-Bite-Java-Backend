package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.dto.LoginRequest;
import com.beansAndBite.beansAndBite.dto.LoginResponse;
import com.beansAndBite.beansAndBite.entity.User;
import com.beansAndBite.beansAndBite.exception.UserNotFoundException;
import com.beansAndBite.beansAndBite.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
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

    public Map<String, Object> signup(@RequestBody @Valid User user) {
        String userName = user.getUsername();
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return signIn(new LoginRequest(userName, password));
    }

    public User authenticate(LoginRequest input) {
        System.out.println("input for authentication " + input.getUserName());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUserName(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmailIdOrMobileNumber(input.getUserName())
                .orElseThrow();
    }

    public Map<String, Object> signIn(LoginRequest loginRequest){

        User authenticatedUser = authenticate(loginRequest);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwtToken);
        response.put("cartCount", authenticatedUser.getCart().size());
        response.put("favouriteCount", authenticatedUser.getFavourites().size());
        response.put("favourite", authenticatedUser.getFavourites());
        response.put("wallet", authenticatedUser.getWallet());

        return response;
    }

}