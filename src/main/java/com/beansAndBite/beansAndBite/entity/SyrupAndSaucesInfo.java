package com.beansAndBite.beansAndBite.entity;

import com.beansAndBite.beansAndBite.enums.SyrupAndSauce;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Embeddable
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SyrupAndSaucesInfo {

    @Enumerated(EnumType.STRING)
    private SyrupAndSauce syrupAndSauce;

    @NotNull(message = "Quantity must not be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

}

