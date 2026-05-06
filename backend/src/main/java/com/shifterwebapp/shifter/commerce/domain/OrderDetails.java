package com.shifterwebapp.shifter.commerce.domain;

import com.shifterwebapp.shifter.catalog.domain.Course;
import com.shifterwebapp.shifter.learning.domain.Enrollment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// todo: indexes
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Price cannot be null")
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull(message = "Discount amount cannot be null")
    @Column(nullable = false)
    private BigDecimal discountAmount;

    @NotNull(message = "Discount percentage cannot be null")
    @Column(nullable = false)
    private BigDecimal discountPercentage;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    //---------MAPPINGS---------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;                          // todo: unique index for pair (course, enrollment)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
