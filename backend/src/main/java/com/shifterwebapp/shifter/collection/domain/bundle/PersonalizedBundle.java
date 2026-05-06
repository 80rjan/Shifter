package com.shifterwebapp.shifter.collection.domain.bundle;

import com.shifterwebapp.shifter.identity.domain.User;
import com.shifterwebapp.shifter.collection.domain.bundle.enums.BundleGeneratedReason;
import com.shifterwebapp.shifter.collection.domain.bundle.enums.PersonalizedBundleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// todo: indexes
public class PersonalizedBundle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PersonalizedBundleType type;

    @NotNull(message = "Generated reason is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BundleGeneratedReason generatedReason;

    @NotNull(message = "Discounted is required")
    @Column(nullable = false)
    private boolean discounted;

    @Builder.Default
    @NotNull(message = "Added discount percentage is required")
    @Column(nullable = false)
    private BigDecimal addedDiscountPercent = BigDecimal.ZERO;

    @Builder.Default
    @NotNull(message = "Added discount amount is required")
    @Column(nullable = false)
    private BigDecimal addedDiscountAmount = BigDecimal.ZERO;

    @Builder.Default
    @NotNull(message = "Active is required")
    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    //---------MAPPINGS---------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bundle_id", nullable = false)
    private Bundle bundle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_bundle_id", nullable = true)
    private CuratedBundle source;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User targetUser;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
