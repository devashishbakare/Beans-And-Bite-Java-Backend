package com.beansAndBite.beansAndBite.entity;

import com.beansAndBite.beansAndBite.enums.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Positive
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

    @ElementCollection
    @CollectionTable(name = "cart_item_syrups", joinColumns = @JoinColumn(name = "cart_item_id"))
    private List<SyrupAndSaucesInfo> syrupAndSaucesInfo = new ArrayList<>();
}
