package com.shifterwebapp.shifter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    private String email;
    @JsonIgnore
    private String passwordHash;    // THIS SHOULD BE CHANGED
    private String name;
    private Boolean isAdmin;
    private CompanyType companyType;
    private String workPosition;
    private ArrayList<Interests> interests;
    private ArrayList<Skills> skills;
    private ArrayList<Skills> skillGap;
    private Integer points;
    private ArrayList<Integer> favoriteCourses;
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