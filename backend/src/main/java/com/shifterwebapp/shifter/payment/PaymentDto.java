package com.shifterwebapp.shifter.payment;

import com.shifterwebapp.shifter.payment.enums.PaymentMethod;
import com.shifterwebapp.shifter.payment.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private Float amount;

    private Date date;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private Integer enrollmentId;
}

