package com.beansAndBite.beansAndBite.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> extends BaseResponse{
    private T data;

    public Response(String message, T data) {
        super(message);
        this.data = data;
    }
}
