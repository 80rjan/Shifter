package com.shifterwebapp.shifter.collection.domain.bundle;

import com.shifterwebapp.shifter.catalog.domain.Course;
import com.shifterwebapp.shifter.collection.domain.bundle.BundleTranslate;
import com.shifterwebapp.shifter.collection.domain.bundle.enums.BundleType;
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
public class Bundle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BundleType type;

    @NotNull(message = "Slug is required")
    @Column(nullable = false)
    private String slug;

    @NotNull(message = "Image url is required")
    @Column(nullable = false)
    private String imageUrl;

    @NotNull(message = "Base price is required")
    @Column(nullable = false)
    private BigDecimal basePrice;

    @NotNull(message = "Discount amount is required")
    @Column(nullable = false)
    private BigDecimal discountAmount;

    @NotNull(message = "Discount percentage is required")
    @Column(nullable = false)
    private BigDecimal discountPercentage;

    @Transient
    public BigDecimal getFinalPrice() {
        BigDecimal discountedPrice = basePrice.subtract(discountAmount);
        return discountedPrice.max(BigDecimal.ZERO);
    }

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime deactivatedAt;

    //---------MAPPINGS---------
    @OneToOne(mappedBy = "bundle", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.EAGER)
    private CuratedBundle curatedBundle;

    @Builder.Default
    @OneToMany(mappedBy = "bundle", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<PersonalizedBundle> personalizedBundles = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "bundle", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.EAGER)
    private List<BundleTranslate> translations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "bundle", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<UserBundle> userBundles = new ArrayList<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "bundle_course",
            joinColumns = @JoinColumn(name = "bundle_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
