package com.shifterwebapp.shifter.account.user.dto;

import com.shifterwebapp.shifter.enums.CompanySize;
import lombok.Data;

@Data
public class PersonalizeUserReq {

    public String name;
    public String workPosition;
    private CompanySize companySize;

}
