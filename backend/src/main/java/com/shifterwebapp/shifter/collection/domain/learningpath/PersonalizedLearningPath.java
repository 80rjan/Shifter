package com.shifterwebapp.shifter.collection.domain.learningpath;

import com.shifterwebapp.shifter.identity.domain.User;
import com.shifterwebapp.shifter.collection.domain.learningpath.enums.PersonalizedLearningPathGeneratedReason;
import com.shifterwebapp.shifter.collection.domain.learningpath.enums.PersonalizedLearningPathType;
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
public class PersonalizedLearningPath {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PersonalizedLearningPathType type;

    @NotNull(message = "Generated reason is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PersonalizedLearningPathGeneratedReason generatedReason;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_path_id", nullable = false)
    private LearningPath learningPath;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_learning_path_id", nullable = true)
    private CuratedLearningPath source;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User targetUser;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
