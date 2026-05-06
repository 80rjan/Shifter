package com.shifterwebapp.shifter.catalog.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class CoursePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Course price must have an amount")
    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private boolean active;

    @NotNull(message = "Course price must specify if it's discounted or not")
    @Column(nullable = false)
    private boolean discounted;

    @NotNull(message = "Course price must have a discount amount if it's discounted")
    @Column(nullable = false)
    private BigDecimal discountAmount;

    @NotNull(message = "Course price must have a discount percentage if it's discounted")
    @Column(nullable = false)
    private BigDecimal discountPercentage;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
