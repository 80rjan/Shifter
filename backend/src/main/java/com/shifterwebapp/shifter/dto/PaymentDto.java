package com.shifterwebapp.shifter.dto;

import com.shifterwebapp.shifter.model.Enrollment;
import com.shifterwebapp.shifter.model.User;
import jakarta.persistence.*;
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
