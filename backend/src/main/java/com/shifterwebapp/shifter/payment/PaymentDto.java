package com.shifterwebapp.shifter.payment;

import com.shifterwebapp.shifter.enums.PaymentMethod;
import com.shifterwebapp.shifter.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private Long id;

    private Double amount;

    private Date date;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;
}

