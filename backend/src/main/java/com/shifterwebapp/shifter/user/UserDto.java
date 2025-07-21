package com.shifterwebapp.shifter.user;

import com.shifterwebapp.shifter.payment.PaymentDto;
import com.shifterwebapp.shifter.enums.CompanyType;
import com.shifterwebapp.shifter.enums.Interests;
import com.shifterwebapp.shifter.enums.Skills;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String email;

    private String name;

    private Boolean hasUsedFreeConsultation;

    private CompanyType companyType;

    private String workPosition;

    private List<Interests> interests;

    private List<Skills> skills;

    private List<Skills> desiredSkills;

    private Integer points;

    private List<Integer> favoriteCourses;
}



