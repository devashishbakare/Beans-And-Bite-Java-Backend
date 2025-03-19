package com.beansAndBite.beansAndBite.controller;

import com.beansAndBite.beansAndBite.dto.SendGiftCardDTO;
import com.beansAndBite.beansAndBite.service.GiftService;
import com.beansAndBite.beansAndBite.util.BaseResponse;
import com.beansAndBite.beansAndBite.util.Response;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gift")
@Slf4j
public class GiftController {

    private final GiftService giftService;
    public GiftController(GiftService giftService){
        this.giftService = giftService;
    }

    @PostMapping("/sendGiftViaWallet")
    public ResponseEntity<BaseResponse> sendGiftToFriend(@RequestBody @Valid SendGiftCardDTO sendGiftCardDTO){
        String updateResponse = giftService.sendGiftViaWallet(sendGiftCardDTO);
        log.info("Controller method response {}", updateResponse);
        Response<String> response = new Response<>("gift has been sent", updateResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

