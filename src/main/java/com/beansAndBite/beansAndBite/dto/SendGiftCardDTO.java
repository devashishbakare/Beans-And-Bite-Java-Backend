package com.beansAndBite.beansAndBite.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SendGiftCardDTO {
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @NotBlank(message = "Gift card image URL cannot be blank")
    private String giftCardImage;

    @NotBlank(message = "Gift card name cannot be blank")
    private String giftCardName;

    @NotBlank(message = "Message cannot be blank")
    private String message;

    @NotBlank(message = "Recipient email cannot be blank")
    @Email(message = "Recipient email must be valid")
    private String recipientEmailId;

    @NotBlank(message = "Recipient mobile number cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "Recipient mobile number must be 10 digits")
    private String recipientMobileNumber;

    @NotBlank(message = "Recipient name cannot be blank")
    private String recipientName;

    @NotBlank(message = "Sender mobile number cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "Sender mobile number must be 10 digits")
    private String senderMobileNumber;

    @NotBlank(message = "Sender name cannot be blank")
    private String senderName;
}
