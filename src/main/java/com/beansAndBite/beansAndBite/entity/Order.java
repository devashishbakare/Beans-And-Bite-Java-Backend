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
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany
    private List<Product> products;

    @NotBlank
    private String takeAwayFrom;
    @Positive
    private Double amount;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String additionalMessage;



}
