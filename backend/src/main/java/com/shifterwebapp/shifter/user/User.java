package com.shifterwebapp.shifter.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.user.enums.CompanyType;
import com.shifterwebapp.shifter.user.enums.Interests;
import com.shifterwebapp.shifter.user.enums.Skills;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;

    private String name;

    private String email;
    
    @JsonIgnore
    private String passwordHash;    // THIS SHOULD BE CHANGED

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

    @OneToMany(mappedBy = "user")       // Maybe CascadeType.PERSIST ????
    private List<Payment> payments;
}

