package com.beansAndBite.beansAndBite.entity;

import com.beansAndBite.beansAndBite.enums.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "order_cartItems",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "cartItem_id")
    )
    private List<CartItem> cartItems;
    @NotBlank
    private String takeAwayFrom;
    @Positive
    private Double amount;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private String additionalMessage;
}
