package com.shifterwebapp.shifter.account.user;

import com.shifterwebapp.shifter.account.Account;
import com.shifterwebapp.shifter.scheduledemail.MeetingEmailReminder;
import com.shifterwebapp.shifter.tag.Tag;
import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.enums.CompanySize;
import com.shifterwebapp.shifter.verificationtoken.VerificationToken;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@Entity
@NoArgsConstructor
@ToString
@Table(name = "_user", indexes = {
        // email is already indexed as unique in Account superclass
        @Index(name = "idx_user_deleted", columnList = "deleted"),
        @Index(name = "idx_user_verified", columnList = "verified")
}) // Using _user to avoid conflict with reserved keyword user in SQL
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

    @Column(nullable = false)
    private Boolean usedFreeConsultation;

    @Enumerated(EnumType.STRING)
    private CompanySize companySize;

    private String workPosition;

    private Integer points = 0;

    @ElementCollection
    private List<Integer> favoriteCourseIds;        // List of course IDs the user has favorited

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)        // when user is saved, enrollments are saved
    private List<Enrollment> enrollments;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})     // when user is saved, tags are saved. NO DELETING ON CASCADE
    @JoinTable(
            name = "user_tag",                                    // join map - table name
            joinColumns = @JoinColumn(name = "user_id"),                // references user.id
            inverseJoinColumns = @JoinColumn(name = "tag_id")     // references tag.id
    )
    private List<Tag> tags;                                 // User's interests and skills gained

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private VerificationToken verificationToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MeetingEmailReminder> meetingEmailReminders;


    // USER DETAILS METHODS
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Example: give ROLE_ADMIN if user.isAdmin() else ROLE_USER
        return List.of(new SimpleGrantedAuthority("USER"));
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
    public void prePersist() {
        if (usedFreeConsultation == null) usedFreeConsultation = false;
        if (points == null) points = 0;
        if (favoriteCourseIds == null) favoriteCourseIds = new ArrayList<>();
    }
}

