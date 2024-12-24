package com.beansAndBite.beansAndBite.entity;

import com.beansAndBite.beansAndBite.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private User user;

    private Product product;

    @Column(nullable = false)
    @NotNull(message = "Amount must not be null")
    @Positive(message = "Amount must be a positive number")
    private Double amount;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    private Milk milk;

    @Enumerated(EnumType.STRING)
    private Espresso espresso;

    @Enumerated(EnumType.STRING)
    private Temperature temperature;

    @Enumerated(EnumType.STRING)
    private WhippedTopping whippedTopping;

    private List<Integer> syrupAndSauces = new ArrayList<>();

}
