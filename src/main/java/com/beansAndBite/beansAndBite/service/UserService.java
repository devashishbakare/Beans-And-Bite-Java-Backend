package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.dto.EditProfileRequestDTO;
import com.beansAndBite.beansAndBite.dto.EditProfileResponse;
import com.beansAndBite.beansAndBite.dto.LoginRequest;
import com.beansAndBite.beansAndBite.dto.SignUpDTO;
import com.beansAndBite.beansAndBite.entity.User;
import com.beansAndBite.beansAndBite.util.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    User signUpUser(User user);
    Map<String, Object> signInUser(LoginRequest loginRequest);

    ResponseEntity<BaseResponse> fetchUserDetails();
    int getCount();
    public EditProfileResponse updateUserDetails(EditProfileRequestDTO editProfileRequestDTO);
    ResponseEntity<BaseResponse> updateUserInfo(EditProfileRequestDTO editProfileRequestDTO);
}
