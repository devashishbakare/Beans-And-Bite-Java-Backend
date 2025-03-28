package com.beansAndBite.beansAndBite.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SendGiftResponse {
    String email;
    double walletAmount;
}
