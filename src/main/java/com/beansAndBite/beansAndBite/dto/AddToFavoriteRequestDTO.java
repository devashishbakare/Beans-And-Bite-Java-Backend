package com.beansAndBite.beansAndBite.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
public class AddToFavoriteRequestDTO {
    private List<Long> favorites;
}
