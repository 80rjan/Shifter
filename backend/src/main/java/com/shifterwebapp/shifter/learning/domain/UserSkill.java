package com.shifterwebapp.shifter.learning.domain;

import com.shifterwebapp.shifter.catalog.domain.Skill;
import com.shifterwebapp.shifter.identity.domain.User;
import com.shifterwebapp.shifter.learning.domain.Enrollment;
import com.shifterwebapp.shifter.shared.domain.enums.SkillProficiency;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user's proficiency in a specific skill.
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// TODO: add indexes
public class UserSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private boolean verified;

    @NotNull(message = "Proficiency cannot be null")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SkillProficiency proficiency;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;


    //---------MAPPINGS---------
    @Builder.Default
    @NotNull(message = "Proficiency score cannot be null")
    @Column(nullable = false)
    private Integer proficiencyScore = 0;       // progress bar in range [0, 100] to visually represent proficiency level

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Builder.Default
    @OneToMany(mappedBy = "userSkill", cascade = {}, fetch = FetchType.LAZY)
    private List<UserSkillSnapshot> userSkillSnapshots = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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
