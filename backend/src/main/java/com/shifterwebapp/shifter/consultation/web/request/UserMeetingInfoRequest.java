package com.shifterwebapp.shifter.consultation.web.request;

import com.shifterwebapp.shifter.identity.domain.enums.CompanySize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserMeetingInfoRequest {

    private String name;

    private String email;

    private CompanySize companySize;

    private String workPosition;

    private String basicInfo;

    private String aboutCompany;

    private String challenges;

    private String otherInfo;
}
