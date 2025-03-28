package com.beansAndBite.beansAndBite.service;

import com.beansAndBite.beansAndBite.dto.GiftHistoryResponse;
import com.beansAndBite.beansAndBite.dto.SendGiftCardDTO;
import com.beansAndBite.beansAndBite.dto.SendGiftResponse;
import com.beansAndBite.beansAndBite.entity.GiftDetails;
import com.beansAndBite.beansAndBite.entity.GiftStatus;
import com.beansAndBite.beansAndBite.entity.User;
import com.beansAndBite.beansAndBite.enums.GiftStatusEnum;
import com.beansAndBite.beansAndBite.exception.InsufficientBalanceException;
import com.beansAndBite.beansAndBite.exception.UserNotFoundException;
import com.beansAndBite.beansAndBite.repository.GiftDetailsRepository;
import com.beansAndBite.beansAndBite.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class GiftServiceImp implements GiftService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GiftDetailsRepository giftDetailsRepository;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SendGiftResponse sendGiftViaWallet(SendGiftCardDTO sendGiftCardDTO) {
        try {

            User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User sender = userRepository.findById(authenticatedUser.getId()).orElseThrow(() -> new UserNotFoundException("sender not found"));
            Hibernate.initialize(sender.getGifts());

            String receiverEmailAddress = sendGiftCardDTO.getRecipientEmailId();
            String receiverMobileNumber = sendGiftCardDTO.getRecipientMobileNumber();

            User receiver = userRepository.findByEmailOrMobileNumberWithTwoArg(receiverEmailAddress, receiverMobileNumber).orElseThrow(() -> new UserNotFoundException("receiver not found"));
            Hibernate.initialize(receiver.getGifts());

            double senderWalletAmountBeforeUpdating = sender.getWallet();
            double receiverWalletAmountBeforeUpdating = receiver.getWallet();

            double giftAmount = sendGiftCardDTO.getAmount();
            if (sender.getWallet() < sendGiftCardDTO.getAmount()) {
                throw new InsufficientBalanceException("Sender has insufficient balance");
            }

            GiftDetails uniqueGiftDetailsForSender = createGiftDetails(sendGiftCardDTO, sender);
            GiftDetails uniqueGiftDetailsForReceiver = createGiftDetails(sendGiftCardDTO, sender);
            giftDetailsRepository.save(uniqueGiftDetailsForSender);
            giftDetailsRepository.save(uniqueGiftDetailsForReceiver);

            GiftStatus senderGiftStatus = createGiftStatus(sender, uniqueGiftDetailsForSender, GiftStatusEnum.Sent);
            GiftStatus receiverGiftStatus = createGiftStatus(receiver, uniqueGiftDetailsForReceiver, GiftStatusEnum.Received);

            sender.getGifts().add(senderGiftStatus);
            receiver.getGifts().add(receiverGiftStatus);
            sender.setWallet(sender.getWallet() - giftAmount);
            receiver.setWallet(receiver.getWallet() + giftAmount);

            userRepository.save(sender);
            userRepository.save(receiver);

            return new SendGiftResponse(sender.getEmail(), sender.getWallet());
        } catch (Exception e) {
            throw new RuntimeException("error while creating gift card" + e.getMessage());
        }
    }
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
                .giftCardImage(sendGiftCardDTO.getGiftCardImage())
                .build();
    }

    private GiftStatus createGiftStatus(User user, GiftDetails giftDetails, GiftStatusEnum status) {
        return GiftStatus.builder()
                .status(status)
                .user(user)
                .giftDetails(giftDetails)
                .build();
    }

    public GiftHistoryResponse giftHistory(int page, int limit){
        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by("createdAt").descending());
        Page<GiftStatus> giftHistory = giftDetailsRepository.findGiftDetailsFromUserId(user.getId(), pageable);
        return new GiftHistoryResponse(giftHistory.getContent(), giftHistory.getTotalElements());
    }

}