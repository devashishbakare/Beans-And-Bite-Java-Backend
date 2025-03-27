package com.beansAndBite.beansAndBite.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EditProfileResponse {
    private String name;
    private String userName;
    private String email;
    private String mobileNumber;
}
