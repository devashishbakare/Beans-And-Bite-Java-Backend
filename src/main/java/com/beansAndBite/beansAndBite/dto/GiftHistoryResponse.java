package com.beansAndBite.beansAndBite.dto;

import com.beansAndBite.beansAndBite.entity.GiftStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GiftHistoryResponse {
    List<GiftStatus> giftDetails;
    long totalGifts;
}
