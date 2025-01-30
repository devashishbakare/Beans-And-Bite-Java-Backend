package com.beansAndBite.beansAndBite.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {
    @NotBlank
    String userName;
    @NotBlank
    String password;
}
