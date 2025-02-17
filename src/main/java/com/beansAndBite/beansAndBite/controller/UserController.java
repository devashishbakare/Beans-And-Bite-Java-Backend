package com.beansAndBite.beansAndBite.controller;
import com.beansAndBite.beansAndBite.dto.LoginRequest;
import com.beansAndBite.beansAndBite.dto.LoginResponse;
import com.beansAndBite.beansAndBite.dto.SignUpDTO;
import com.beansAndBite.beansAndBite.entity.Product;
import com.beansAndBite.beansAndBite.entity.User;
import com.beansAndBite.beansAndBite.enums.Category;
import com.beansAndBite.beansAndBite.exception.ErrorResponse;
import com.beansAndBite.beansAndBite.service.AuthenticationService;
import com.beansAndBite.beansAndBite.service.JwtService;
import com.beansAndBite.beansAndBite.service.UserService;
import com.beansAndBite.beansAndBite.util.BaseResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
//@Tag(name = "User", description = "Handles user-related operations")
public class UserController {

    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationService authenticationService;

    public UserController(JwtService jwtService, AuthenticationService authenticationService, UserService userService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/signUp")
    //@Operation(summary = "Sign Up", description = "Register a new user")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpDTO signUpData){
        try{
            User user = new User(signUpData.getName(), signUpData.getEmail(), signUpData.getMobileNumber(), signUpData.getPassword());
            System.out.println("before validating user");
            Map<String, Object> userInfo = authenticationService.signup(user);
            Map<String, Object> storeResponse = Map.of("message", "Sign Up user successfully",
                    "data" , userInfo);
            return ResponseEntity.status(HttpStatus.OK).body(storeResponse);
        }catch(Exception ex){
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage() + " something went wrong while user sign up", LocalDateTime.now().toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/signIn")
    //@Operation(summary = "Sign In", description = "Login User")
    public ResponseEntity<?> signIn(@RequestBody @Valid LoginRequest loginRequest){
        try{
            System.out.println("before authentication");
            Map<String, Object> userInfo = authenticationService.signIn(loginRequest);
            Map<String, Object> storeResponse = Map.of("message", "User login successful",
                    "data" , userInfo);
            return ResponseEntity.status(HttpStatus.OK).body(storeResponse);
        }catch(Exception ex){
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage() + " something went wrong while signing in", LocalDateTime.now().toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/details")
    private ResponseEntity<BaseResponse> userDetails(){
        System.out.println("from controller route");
        return userService.fetchUserDetails();
    }
}
