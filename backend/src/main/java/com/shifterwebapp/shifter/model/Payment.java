package com.shifterwebapp.shifter.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_sequence", allocationSize = 1)
    private Integer id;

    private Float amount;

    private Date date;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "payment")
    private Enrollment enrollment;
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