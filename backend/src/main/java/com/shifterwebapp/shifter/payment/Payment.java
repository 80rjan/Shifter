package com.shifterwebapp.shifter.payment;

import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.payment.enums.PaymentMethod;
import com.shifterwebapp.shifter.payment.enums.PaymentStatus;
import com.shifterwebapp.shifter.user.User;
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
    private Long id;

    private Float amount;

    private Date date;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Enrollment enrollment;
}

