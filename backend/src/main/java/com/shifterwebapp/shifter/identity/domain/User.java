package com.shifterwebapp.shifter.identity.domain;

import com.shifterwebapp.shifter.identity.domain.enums.AccountAuthority;
import com.shifterwebapp.shifter.collection.domain.bundle.PersonalizedBundle;
import com.shifterwebapp.shifter.collection.domain.bundle.UserBundle;
import com.shifterwebapp.shifter.learning.domain.Certificate;
import com.shifterwebapp.shifter.catalog.domain.Course;
import com.shifterwebapp.shifter.catalog.domain.CourseActivityEvent;
import com.shifterwebapp.shifter.collection.domain.learningpath.PersonalizedLearningPath;
import com.shifterwebapp.shifter.collection.domain.learningpath.UserLearningPath;
import com.shifterwebapp.shifter.consultation.domain.MeetingEmailReminder;
import com.shifterwebapp.shifter.learning.domain.Enrollment;
import com.shifterwebapp.shifter.identity.domain.enums.CompanySize;
import com.shifterwebapp.shifter.catalog.domain.Topic;
import com.shifterwebapp.shifter.learning.domain.UserSkill;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@Entity
@NoArgsConstructor
@ToString
@Table(name = "_user") // Using _user to avoid conflict with reserved keyword user in SQL
// TODO: indexes
public class User extends Account {

    @Column(nullable = false)
    // default false because primitive obj
    private boolean verified;                     // For email verification

    @Column(nullable = false)
    // default false because primitive obj
    private boolean profileComplete;              // To check if user has completed profile (personalization)

    @Column(nullable = false)
    // default false because primitive obj
    private boolean deleted;                      // Soft delete flag

    @Builder.Default
    @Column(nullable = false)
    private Boolean usedFreeConsultation = false;

    @Enumerated(EnumType.STRING)
    private CompanySize companySize;

    private String workPosition;

    @Builder.Default
    private Integer points = 0;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;


    //---------MAPPINGS---------
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = {}, fetch = FetchType.LAZY)
    private List<Enrollment> enrollments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = {}, fetch = FetchType.LAZY)
    private List<CourseActivityEvent> courseActivityEvents = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = {}, fetch = FetchType.LAZY)
    private List<UserLearningPath> userLearningPaths = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = {}, fetch = FetchType.LAZY)
    private List<UserBundle> userBundles = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "targetUser", cascade = {}, fetch = FetchType.LAZY)
    private List<PersonalizedBundle> personalizedBundles = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "targetUser", cascade = {}, fetch = FetchType.LAZY)
    private List<PersonalizedLearningPath> personalizedLearningPaths = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_favorite_course",
            joinColumns = @JoinColumn(name = "user_id"),                // references User.id
            inverseJoinColumns = @JoinColumn(name = "course_id")        // references Course.id
    )
    private List<Course> favoriteCourses;

    @ManyToMany(fetch = FetchType.EAGER)     // TODO: fetch type?
    @JoinTable(
            name = "user_topic",
            joinColumns = @JoinColumn(name = "user_id"),                // references User.id
            inverseJoinColumns = @JoinColumn(name = "topic_id")         // references Topic.id
    )
    private List<Topic> interestedTopics;

    @OneToMany(mappedBy = "user", cascade = {}, fetch = FetchType.EAGER)  // TODO: fetch type?
    private List<UserSkill> acquiredSkills;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = {}, fetch = FetchType.LAZY)
    private List<MeetingEmailReminder> meetingEmailReminders = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private VerificationToken verificationToken;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = {}, fetch = FetchType.LAZY)
    private List<Certificate> certificates = new ArrayList<>();

    // USER DETAILS METHODS
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Example: give ROLE_ADMIN if user.isAdmin() else ROLE_USER
        return List.of(new SimpleGrantedAuthority(AccountAuthority.ROLE_USER.toString()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


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

