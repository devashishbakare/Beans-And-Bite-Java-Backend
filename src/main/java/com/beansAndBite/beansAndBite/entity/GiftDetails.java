package com.beansAndBite.beansAndBite.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Table(name = "gift_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class GiftDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false, length = 255)
    private String recipientName;

    @Column(nullable = false)
    @Pattern(regexp = "^[0-9]{10}$", message = "mobile number must be 10 digit and valid")
    private String recipientMobileNumber;

    @Email(message = "email must be valid")
    @Column(nullable = false, length = 255)
    private String recipientEmailAddress;

    @Column(nullable = false, length = 255)
    private String senderName;

    @Column(nullable = false)
    @Pattern(regexp = "^[0-9]{10}$", message = "mobile number must be 10 digit and valid")
    private String senderMobileNumber;

    @Email(message = "email must be valid")
    @Column(nullable = false, length = 255)
    private String senderEmailAddress;

    private String senderMessage;

    @Column(nullable = false, length = 500)
    private String giftCardImage;

}
