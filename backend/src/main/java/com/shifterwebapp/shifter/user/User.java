package com.shifterwebapp.shifter.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.enums.CompanyType;
import com.shifterwebapp.shifter.enums.Interests;
import com.shifterwebapp.shifter.enums.Skills;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "_user") // Using _user to avoid conflict with reserved keyword in SQL
public class User implements UserDetails {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
//    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;
    
    @JsonIgnore
    private String passwordHash;    // SHOULD I USE JSON IGNORE HERE? OR IS IT ENOUGH TO NOT EXPOSE IT IN DTO?

    private Boolean isAdmin;
    
    private CompanyType companyType;
    
    private String workPosition;
    
    @ElementCollection(targetClass = Interests.class)
    @Enumerated(EnumType.STRING)
    private List<Interests> interests;

    @ElementCollection(targetClass = Skills.class)
    @Enumerated(EnumType.STRING)
    private List<Skills> skills;

    @ElementCollection(targetClass = Skills.class)
    @Enumerated(EnumType.STRING)
    private List<Skills> skillGap;
    
    private Integer points;

    @ElementCollection
    private List<Integer> favoriteCourses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Payment> payments;             // WHEN DELETING USER SET PAYMENTS TO NULL, BECAUSE PAYMENTS DONT GET DELETED



    // USER DETAILS METHODS
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Example: give ROLE_ADMIN if user.isAdmin() else ROLE_USER
        return isAdmin
                ? List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
                : List.of(new SimpleGrantedAuthority("ROLE_USER"));
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

