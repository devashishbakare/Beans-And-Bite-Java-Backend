package com.beansAndBite.beansAndBite.entity;

import com.beansAndBite.beansAndBite.enums.SyrupAndSauce;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SyrupAndSaucesInfo {
    @Enumerated(EnumType.STRING)
    private SyrupAndSauce syrupAndSauce;
    @NotNull(message = "Quantity must not be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}
