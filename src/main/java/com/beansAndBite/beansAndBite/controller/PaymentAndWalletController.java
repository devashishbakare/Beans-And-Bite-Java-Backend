package com.beansAndBite.beansAndBite.controller;

import com.beansAndBite.beansAndBite.dto.AddToWallerDTO;
import com.beansAndBite.beansAndBite.dto.LoginResponseDTO;
import com.beansAndBite.beansAndBite.service.PaymentAndWallerService;
import com.beansAndBite.beansAndBite.service.UserService;
import com.beansAndBite.beansAndBite.util.BaseResponse;
import com.beansAndBite.beansAndBite.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")


public class PaymentAndWalletController {

    private final PaymentAndWallerService paymentAndWallerService;
    public PaymentAndWalletController(PaymentAndWallerService  paymentAndWallerService){
        this.paymentAndWallerService = paymentAndWallerService;
    }
    @PostMapping("/addToWallet")
    public ResponseEntity<BaseResponse> addMoneyToWaller(@RequestBody AddToWallerDTO addToWallerDTO){
        double amount = addToWallerDTO.getAmount();
        double updatedAmount = paymentAndWallerService.addToWaller(amount);
        Response<Double> response = new Response<>("Updated user wallet amount", updatedAmount);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
