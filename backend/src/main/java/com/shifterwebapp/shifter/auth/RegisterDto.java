package com.shifterwebapp.shifter.auth;

import com.shifterwebapp.shifter.enums.CompanySize;
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

    private CompanySize companySize;

    private String workPosition;

    private List<String> interests;

    private List<String> desiredSkills;
}
