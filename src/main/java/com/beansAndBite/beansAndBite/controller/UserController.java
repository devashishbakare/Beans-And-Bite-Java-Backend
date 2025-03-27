package com.beansAndBite.beansAndBite.controller;
import com.beansAndBite.beansAndBite.dto.*;
import com.beansAndBite.beansAndBite.entity.Product;
import com.beansAndBite.beansAndBite.entity.User;
import com.beansAndBite.beansAndBite.enums.Category;
import com.beansAndBite.beansAndBite.exception.ErrorResponse;
import com.beansAndBite.beansAndBite.service.AuthenticationService;
import com.beansAndBite.beansAndBite.service.JwtService;
import com.beansAndBite.beansAndBite.service.UserService;
import com.beansAndBite.beansAndBite.util.BaseResponse;
import com.beansAndBite.beansAndBite.util.ErrorInfo;
import com.beansAndBite.beansAndBite.util.Response;
import com.fasterxml.jackson.databind.ser.Serializers;
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
    public ResponseEntity<BaseResponse> signUp(@RequestBody @Valid SignUpDTO signUpData){
        try{
            User user = new User(signUpData.getName(), signUpData.getEmail(), signUpData.getMobileNumber(), signUpData.getPassword());
            System.out.println("before validating user");
            LoginResponseDTO userDetails = authenticationService.signup(user);
            Response<LoginResponseDTO> response = new Response<>("user login successful", userDetails);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(Exception ex){
            ErrorInfo errorInfo = new ErrorInfo(HttpStatus.OK.value(), "something went wrong" + ex.getMessage(), LocalDateTime.now().toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorInfo);
        }
    }

    @PostMapping("/signIn")
    //@Operation(summary = "Sign In", description = "Login User")
    public ResponseEntity<BaseResponse> signIn(@RequestBody @Valid LoginRequest loginRequest){
        try{
            System.out.println("before authentication");
            LoginResponseDTO loginDetails = authenticationService.signIn(loginRequest);
            Response<LoginResponseDTO> response = new Response<>("user login successful", loginDetails);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch(Exception ex){
            ErrorInfo errorInfo = new ErrorInfo(HttpStatus.OK.value(), "something went wrong" + ex.getMessage(), LocalDateTime.now().toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorInfo);
        }
    }

    @GetMapping("/details")
    private ResponseEntity<BaseResponse> userDetails(){
        System.out.println("from controller route");
        return userService.fetchUserDetails();
    }

    @GetMapping("/getCartCount")
    private ResponseEntity<BaseResponse> getCount(){
        int count = userService.getCount();
        Response<Integer> response = new Response<>("here is user cart count", count);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/editAccount")
    private ResponseEntity<BaseResponse> updateUserDetails(@RequestBody @Valid  SignUpDTO userInfo){
        Response<EditProfileResponse> response = new Response<>("updated user details", userService.updateUserDetails(userInfo));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
