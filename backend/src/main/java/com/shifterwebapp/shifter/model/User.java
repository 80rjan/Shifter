package com.shifterwebapp.shifter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    private Integer id;
    
    private String email;
    
    @JsonIgnore
    private String passwordHash;    // THIS SHOULD BE CHANGED

    private String name;
    
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

    @OneToMany(mappedBy = "user")
    private List<Payment> payments;
}

enum CompanyType {
    FREELANCE,
    STARTUP,
    SME,
    MIDMARKET,
    ENTERPRISE,
    OTHER
}

enum Interests {
    SALES_STRATEGIES,
    MARKETING,
    LEADERSHIP,
    MANAGEMENT,
    DIGITAL_TRANSFORMATION,
    BUSINESS_TRANSFORMATION,
    ENTREPRENEURSHIP,
    STARTUP,
    SALES,
    NEGOTIATION,
    FINANCE_FOR_BUSINESS
}

enum Skills {
    COMMUNICATION,
    STRATEGIC_PLANING,
    LEADERSHIP,
    SALES_TECHNIQUES,
    SALES,
    NEGOTIATION,
    MARKETING,
    PROBLEM_SOLVING,
}