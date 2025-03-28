package com.beansAndBite.beansAndBite.service;
import com.beansAndBite.beansAndBite.dto.EditProfileRequestDTO;
import com.beansAndBite.beansAndBite.dto.EditProfileResponse;
import com.beansAndBite.beansAndBite.dto.LoginRequest;
import com.beansAndBite.beansAndBite.dto.SignUpDTO;
import com.beansAndBite.beansAndBite.entity.User;
import com.beansAndBite.beansAndBite.exception.AuthenticationException;
import com.beansAndBite.beansAndBite.exception.UserNotFoundException;
import com.beansAndBite.beansAndBite.repository.UserRepository;
import com.beansAndBite.beansAndBite.util.BaseResponse;
import com.beansAndBite.beansAndBite.util.ErrorAPIResponse;
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

            EditProfileResponse editProfileResponse = new EditProfileResponse(user.getName(), user.getUsername(), user.getEmail(), user.getMobileNumber());

            Response<EditProfileResponse> response = new Response<>("user details", editProfileResponse);
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

    public EditProfileResponse updateUserDetails(EditProfileRequestDTO editProfileRequestDTO){
        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = user.getId();
        user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
        user.setName(editProfileRequestDTO.getUserName());
        user.setEmail(editProfileRequestDTO.getEmail());
        user.setMobileNumber(editProfileRequestDTO.getMobileNumber());
        user.setPassword(passwordEncoder.encode(editProfileRequestDTO.getPassword()));
        userRepository.save(user);
        return new EditProfileResponse(user.getName(), user.getUsername(), user.getEmail(), user.getMobileNumber());
    }

    public ResponseEntity<BaseResponse> updateUserInfo(EditProfileRequestDTO editProfileRequestDTO){

        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = user.getId();
        user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));

        if(!user.getEmail().equals(editProfileRequestDTO.getEmail())){
            if(userRepository.existsByEmail(editProfileRequestDTO.getEmail())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorAPIResponse("", "Try different email or password"));
            }
        }
        if(!user.getMobileNumber().equals(editProfileRequestDTO.getMobileNumber())){
            if(userRepository.existsByMobileNumber(editProfileRequestDTO.getEmail())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorAPIResponse("", "Try different email or password"));
            }
        }

        user.setName(editProfileRequestDTO.getUserName());
        user.setEmail(editProfileRequestDTO.getEmail());
        user.setMobileNumber(editProfileRequestDTO.getMobileNumber());
        user.setPassword(passwordEncoder.encode(editProfileRequestDTO.getPassword()));
        userRepository.save(user);

       EditProfileResponse editProfileResponse =  new EditProfileResponse(user.getName(), user.getUsername(), user.getEmail(), user.getMobileNumber());
        return ResponseEntity.status(HttpStatus.OK).body(new Response<EditProfileResponse>("User has been updated", editProfileResponse));
    }

}


