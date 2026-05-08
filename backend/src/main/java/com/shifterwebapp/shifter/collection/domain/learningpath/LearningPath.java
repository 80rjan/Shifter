package com.shifterwebapp.shifter.collection.domain.learningpath;

import com.shifterwebapp.shifter.shared.domain.enums.Difficulty;
import com.shifterwebapp.shifter.collection.domain.learningpath.enums.LearningPathType;
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
public class LearningPath {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LearningPathType type;

    @NotNull(message = "Slug is required")
    @Column(nullable = false)
    private String slug;

    @NotNull(message = "Image url is required")
    @Column(nullable = false)
    private String imageUrl;

    @NotNull(message = "Base price is required")
    @Column(nullable = false)
    private BigDecimal basePrice;

    @NotNull(message = "Discount boolean flag is required")
    @Column(nullable = false)
    private boolean discounted;

    @NotNull(message = "Discount amount is required")
    @Column(nullable = false)
    private BigDecimal discountAmount;

    @NotNull(message = "Discount percentage is required")
    @Column(nullable = false)
    private BigDecimal discountPercentage;

    @Transient
    public BigDecimal getFinalPrice() {
        if (!discounted) {
            return basePrice;
        }
        BigDecimal discountedPrice = basePrice.subtract(discountAmount);
        return discountedPrice.max(BigDecimal.ZERO);
    }

    @NotNull(message = "Estimated duration in hours is required")
    @Column(nullable = false)
    private Integer estimatedDurationHours;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Difficulty is required")
    @Column(nullable = false)
    private Difficulty difficulty;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime deactivatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "learningPath", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.EAGER)
    private List<LearningPathTranslation> translations = new ArrayList<>();

    @OneToOne(mappedBy = "learningPath", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.EAGER)
    private CuratedLearningPath curatedLearningPath;

    @Builder.Default
    @OneToMany(mappedBy = "learningPath", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<PersonalizedLearningPath> personalizedLearningPaths = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "learningPath", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<LearningPathCourse> learningPathCourses = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "learningPath", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<UserLearningPath> userLearningPaths = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
