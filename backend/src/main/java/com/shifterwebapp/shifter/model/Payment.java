package com.shifterwebapp.shifter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Payment  {
    private Float amount;
    private Date date;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
}

enum PaymentMethod {
    CARD,
    PAYPAL,
    CASYS
}

enum PaymentStatus {
    PENDING,
    COMPLETED,
    FAILED
}