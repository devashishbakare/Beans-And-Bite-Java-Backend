package com.beansAndBite.beansAndBite.service;
import com.beansAndBite.beansAndBite.dto.SendGiftCardDTO;

public interface GiftService{
    String sendGiftViaWallet(SendGiftCardDTO sendGiftCardDTO);
}


