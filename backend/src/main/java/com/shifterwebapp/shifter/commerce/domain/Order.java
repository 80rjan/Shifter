package com.shifterwebapp.shifter.commerce.domain;

import com.shifterwebapp.shifter.commerce.domain.enums.OrderStatus;
import com.shifterwebapp.shifter.commerce.domain.Payment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "_order") // "order" is a reserved keyword in SQL, so we use "_order" as the table name
// todo: indexes
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Order status cannot be null")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    //---------MAPPINGS---------
    @OneToOne(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Payment payment;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.EAGER)
    private List<OrderDetails> orderDetails;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
