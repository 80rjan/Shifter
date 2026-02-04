package com.shifterwebapp.shifter.account.user.dto;

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

    private Boolean usedFreeConsultation;

    private CompanySize companySize;

    private String workPosition;

    private List<String> interests;

    private List<String> skillsGained;

    private Integer points;

    private List<Integer> favoriteCourseIds;

    private Boolean profileComplete;
}



