package com.shifterwebapp.shifter.external.meeting;

import com.shifterwebapp.shifter.enums.CompanySize;
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

    private String aboutCompany;

    private String challenges;

    private String expectations;

    private String otherInfo;
}
