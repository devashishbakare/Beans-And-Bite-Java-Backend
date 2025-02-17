package com.beansAndBite.beansAndBite.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class LoginResponseDTO {
    private String token;
    private int cartCount;
    private int favouriteCount;
    private List<Long> favourites = new ArrayList<>();
    private Double wallet;
}
