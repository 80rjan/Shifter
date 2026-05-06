package com.shifterwebapp.shifter.commerce.web.response;

import com.shifterwebapp.shifter.commerce.domain.enums.PaymentMethod;
import com.shifterwebapp.shifter.commerce.domain.enums.PaymentStatus;
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

