package com.shifterwebapp.shifter.auth;

import com.shifterwebapp.shifter.enums.CompanySize;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class UserPersonalizationDto {

    @NotBlank
    private String name;

    private String email;

    private CompanySize companySize;

    private String workPosition;

    private List<String> interests;

    private List<String> desiredSkills;
}
