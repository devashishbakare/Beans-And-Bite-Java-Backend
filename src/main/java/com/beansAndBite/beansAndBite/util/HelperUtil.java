package com.beansAndBite.beansAndBite.util;

import com.beansAndBite.beansAndBite.dto.SendGiftCardDTO;
import com.beansAndBite.beansAndBite.entity.GiftDetails;
import com.beansAndBite.beansAndBite.entity.GiftStatus;
import com.beansAndBite.beansAndBite.entity.User;
import com.beansAndBite.beansAndBite.enums.GiftStatusEnum;

public class HelperUtil {
    private GiftDetails createGiftDetails(SendGiftCardDTO sendGiftCardDTO, User sender) {
        return GiftDetails.builder()
                .name(sendGiftCardDTO.getGiftCardName())
                .amount(sendGiftCardDTO.getAmount())
                .recipientName(sendGiftCardDTO.getRecipientName())
                .recipientMobileNumber(sendGiftCardDTO.getRecipientMobileNumber())
                .recipientEmailAddress(sendGiftCardDTO.getRecipientEmailId())
                .senderName(sendGiftCardDTO.getSenderName())
                .senderMobileNumber(sendGiftCardDTO.getSenderMobileNumber())
                .senderEmailAddress(sender.getEmail())
                .senderMessage(sendGiftCardDTO.getMessage())
                .build();
    }

    private GiftStatus createGiftStatus(User user, GiftDetails giftDetails, GiftStatusEnum status) {
        return GiftStatus.builder()
                .status(status)
                .user(user)
                .giftDetails(giftDetails)
                .build();
    }
}
