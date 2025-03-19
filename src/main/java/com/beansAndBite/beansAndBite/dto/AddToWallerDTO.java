package com.beansAndBite.beansAndBite.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToWallerDTO {
    @Positive
    private double amount;
}
