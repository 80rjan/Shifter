package com.shifterwebapp.shifter.meeting;

import com.shifterwebapp.shifter.enums.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserMeetingInfoRequest {

    private String name;

    private String email;

    private CompanyType companyType;

    private String workPosition;

    private String aboutCompany;

    private String challenges;

    private String expectations;

    private String otherInfo;
}
