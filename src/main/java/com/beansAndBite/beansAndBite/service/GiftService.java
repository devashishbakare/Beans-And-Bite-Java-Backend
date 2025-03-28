package com.beansAndBite.beansAndBite.service;
import com.beansAndBite.beansAndBite.dto.GiftHistoryResponse;
import com.beansAndBite.beansAndBite.dto.SendGiftCardDTO;
import com.beansAndBite.beansAndBite.dto.SendGiftResponse;
import com.beansAndBite.beansAndBite.entity.GiftStatus;

import java.util.List;

public interface GiftService{
    SendGiftResponse sendGiftViaWallet(SendGiftCardDTO sendGiftCardDTO);
    GiftHistoryResponse giftHistory(int page, int limit);
}


