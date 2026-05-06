package com.shifterwebapp.shifter.commerce.domain;

import com.shifterwebapp.shifter.commerce.domain.enums.PaymentMethod;
import com.shifterwebapp.shifter.commerce.domain.enums.PaymentStatus;
import com.shifterwebapp.shifter.commerce.domain.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// TODO: add more indexes when creating admin dashboard features
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Amount cannot be null")
    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDate date;

    @NotNull(message = "Payment method cannot be null")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @NotNull(message = "Payment status cannot be null")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @PrePersist
    protected void onCreate() {
        date = LocalDate.now();
    }
}

