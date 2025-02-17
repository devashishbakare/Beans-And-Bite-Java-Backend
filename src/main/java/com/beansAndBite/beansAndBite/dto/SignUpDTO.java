package com.beansAndBite.beansAndBite.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDTO {
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 255, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

    @NotBlank(message = "Form type is required")
    private String formType;

}
