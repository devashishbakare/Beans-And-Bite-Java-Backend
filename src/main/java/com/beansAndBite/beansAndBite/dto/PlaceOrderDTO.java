package com.beansAndBite.beansAndBite.dto;
import java.util.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public class PlaceOrderDTO {
    private String additionalMessage;

    @Positive
    private double amount;

    @NotBlank(message = "payment mode has to be set")
    private String paymentMethod;

    private List<Long> products = new ArrayList<>();

    @NotBlank(message = "take away location is must")
    private String takeAwayFrom;

    // Getters and Setters
    public String getAdditionalMessage() {
        return additionalMessage;
    }

    public void setAdditionalMessage(String additionalMessage) {
        this.additionalMessage = additionalMessage;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<Long> getProducts() {
        return products;
    }

    public void setProducts(List<Long> products) {
        this.products = products;
    }

    public String getTakeAwayFrom() {
        return takeAwayFrom;
    }

    public void setTakeAwayFrom(String takeAwayFrom) {
        this.takeAwayFrom = takeAwayFrom;
    }
}
