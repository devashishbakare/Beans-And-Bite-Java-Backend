package com.beansAndBite.beansAndBite.controller;

import com.beansAndBite.beansAndBite.dto.GiftHistoryResponse;
import com.beansAndBite.beansAndBite.dto.SendGiftCardDTO;
import com.beansAndBite.beansAndBite.dto.SendGiftResponse;
import com.beansAndBite.beansAndBite.entity.GiftStatus;
import com.beansAndBite.beansAndBite.service.GiftService;
import com.beansAndBite.beansAndBite.util.BaseResponse;
import com.beansAndBite.beansAndBite.util.Response;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        SendGiftResponse sendGiftResponse = giftService.sendGiftViaWallet(sendGiftCardDTO);
        Response<SendGiftResponse> response = new Response<>("gift has been sent", sendGiftResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/history")
    public ResponseEntity<BaseResponse> fetchGiftHistory(@RequestParam int page, @RequestParam int limit){
        Response<GiftHistoryResponse> response = new Response<>("gift has been sent", giftService.giftHistory(page, limit));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

