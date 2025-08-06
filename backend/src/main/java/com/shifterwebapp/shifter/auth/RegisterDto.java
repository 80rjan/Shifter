package com.shifterwebapp.shifter.auth;

import com.shifterwebapp.shifter.enums.CompanyType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class RegisterDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    private CompanyType companyType;

    private String workPosition;

    private List<String> interests;

    private List<String> desiredSkills;
}
