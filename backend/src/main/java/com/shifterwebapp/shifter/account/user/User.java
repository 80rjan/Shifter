package com.shifterwebapp.shifter.account.user;

import com.shifterwebapp.shifter.account.Account;
import com.shifterwebapp.shifter.attribute.Attribute;
import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.enums.CompanySize;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.verificationtoken.VerificationToken;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@Entity
@NoArgsConstructor
@ToString
@Table(name = "_user") // Using _user to avoid conflict with reserved keyword in SQL
public class User extends Account {

    private boolean verified;                     // For email verification

    private boolean profileComplete;              // To check if user has completed profile (personalization)

    private Boolean usedFreeConsultation;

    @Enumerated(EnumType.STRING)
    private CompanySize companySize;

    private String workPosition;

    private Integer points;

    @ElementCollection
    private List<Integer> favoriteCourseIds;        // List of course IDs the user has favorited

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)        // when user is saved, enrollments are saved
    private List<Enrollment> enrollments;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})     // when user is saved, attributes are saved. NO DELETING ON CASCADE
    @JoinTable(
            name = "user_attribute",                                    // join map - table name
            joinColumns = @JoinColumn(name = "user_id"),                // references user.id
            inverseJoinColumns = @JoinColumn(name = "attribute_id")     // references attribute.id
    )
    private List<Attribute> attributes;                                 // User's interests and skills gained

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = true)
    private VerificationToken verificationToken;


    // USER DETAILS METHODS
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Example: give ROLE_ADMIN if user.isAdmin() else ROLE_USER
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
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
}

