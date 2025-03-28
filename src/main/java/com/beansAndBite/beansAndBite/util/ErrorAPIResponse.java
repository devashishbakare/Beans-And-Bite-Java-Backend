package com.beansAndBite.beansAndBite.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorAPIResponse extends BaseResponse{
    private String error;
    public ErrorAPIResponse(String message, String error){
        super(message);
        this.error = error;
    }
}
