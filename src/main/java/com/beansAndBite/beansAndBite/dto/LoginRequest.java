package com.beansAndBite.beansAndBite.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest {
    @NotBlank
    String userName;
    @NotBlank
    String password;
    @NotBlank(message = "Form type is required")
    private String formType;
}
