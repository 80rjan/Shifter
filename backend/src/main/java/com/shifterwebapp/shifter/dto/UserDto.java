package com.shifterwebapp.shifter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String email;

    private String name;

    private CompanyType companyType;

    private String workPosition;

    private List<Interests> interests;

    private List<Skills> skills;

    private List<Skills> skillGap;

    private Integer points;

    private List<Integer> favoriteCourses;

    private List<PaymentDto> payments;
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