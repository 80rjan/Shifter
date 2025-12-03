package com.shifterwebapp.shifter.user.dto;

import com.shifterwebapp.shifter.attribute.Attribute;
import com.shifterwebapp.shifter.attribute.dto.AttributeDto;
import com.shifterwebapp.shifter.enums.CompanySize;
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

    private CompanySize companySize;

    private String workPosition;

    private List<String> interests;

    private List<String> skillsGained;

    private Integer points;

    private List<Integer> favoriteCourses;

    private Boolean isAdmin;

    private Boolean isProfileComplete;
}



