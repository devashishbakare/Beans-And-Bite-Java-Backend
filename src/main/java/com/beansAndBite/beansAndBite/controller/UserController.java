package com.beansAndBite.beansAndBite.controller;


import com.beansAndBite.beansAndBite.dto.LoginRequest;
import com.beansAndBite.beansAndBite.entity.User;
import com.beansAndBite.beansAndBite.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
//@Tag(name = "User", description = "Handles user-related operations")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signUp")
    //@Operation(summary = "Sign Up", description = "Register a new user")
    public ResponseEntity<?> signUp(@RequestBody @Valid User user){
        String token = userService.signUpUser(user);
        return ResponseEntity.ok(Map.of(
                "message", "User Register successfully",
                "token", token,
                "userDetails", user
        ));
    }

    @PostMapping("/signIn")
    //@Operation(summary = "Sign In", description = "Login User")
    public ResponseEntity<?> signIn(@RequestBody @Valid LoginRequest loginRequest){
        System.out.println(loginRequest.toString());
        String token = userService.signInUser(loginRequest);
        return ResponseEntity.ok(Map.of(
                "Message" , "user login successfull",
                "token", token
        ));
    }
}
