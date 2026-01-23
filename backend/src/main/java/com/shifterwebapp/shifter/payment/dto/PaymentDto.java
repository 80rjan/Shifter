package com.shifterwebapp.shifter.payment.dto;

import com.shifterwebapp.shifter.enums.PaymentMethod;
import com.shifterwebapp.shifter.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private Long id;

    private Double amount;

    private LocalDate paymentDate;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;
}

