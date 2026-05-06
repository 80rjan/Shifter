package com.shifterwebapp.shifter.identity.web.request;

import com.shifterwebapp.shifter.identity.domain.enums.CompanySize;
import com.shifterwebapp.shifter.shared.domain.LanguageCode;
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

    private LanguageCode language;

    private List<Long> tagIdList;
}
