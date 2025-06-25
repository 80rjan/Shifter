package com.shifterwebapp.shifter.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.enums.CompanyType;
import com.shifterwebapp.shifter.enums.Interests;
import com.shifterwebapp.shifter.enums.Skills;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    @SequenceGenerator(name = "account_seq", sequenceName = "account_sequence", allocationSize = 1)
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

    @OneToMany(mappedBy = "account", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Payment> payments;             // WHEN DELETING account SET PAYMENTS TO NULL, BECAUSE PAYMENTS DONT GET DELETED
}

