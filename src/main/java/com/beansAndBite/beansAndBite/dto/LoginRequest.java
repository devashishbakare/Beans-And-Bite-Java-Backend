package com.beansAndBite.beansAndBite.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank
    String userName;
    @NotBlank
    String password;
}
