package com.shifterwebapp.shifter.collection.domain.learningpath;

import com.shifterwebapp.shifter.identity.domain.User;
import com.shifterwebapp.shifter.collection.domain.learningpath.enums.UserLearningPathStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// todo: indexes
public class UserLearningPath {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private LocalDate acquiredDate;

    @Column(nullable = true)
    private LocalDate completedDate;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserLearningPathStatus status = UserLearningPathStatus.NOT_STARTED;

    @Builder.Default
    @Column(nullable = false)
    private Integer progressPercentage = 0;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_path_id", nullable = false)
    private LearningPath learningPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    protected void onCreate() {
        acquiredDate = LocalDate.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
