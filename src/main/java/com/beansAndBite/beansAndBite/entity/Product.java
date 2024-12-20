package com.beansAndBite.beansAndBite.entity;

import com.beansAndBite.beansAndBite.enums.Category;
import com.beansAndBite.beansAndBite.enums.ProductType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 500)
    private String productInfo;

    @Column(nullable = false, length = 500)
    private String productDetails;

    @Column
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false, length = 500)
    private String productCartImage;

    @Column(nullable = false, length = 500)
    private String productDetailsImage;

    @Column
    @Enumerated(EnumType.STRING)
    private ProductType productType;

}
