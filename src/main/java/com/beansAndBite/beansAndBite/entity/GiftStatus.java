package com.beansAndBite.beansAndBite.entity;

import com.beansAndBite.beansAndBite.enums.GiftStatusEnum;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "gift_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class GiftStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private GiftStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "gift_details_id", referencedColumnName = "id", nullable = false)
    private GiftDetails giftDetails;
}
