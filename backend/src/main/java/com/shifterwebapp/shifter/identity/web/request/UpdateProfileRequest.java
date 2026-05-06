package com.shifterwebapp.shifter.identity.web.request;

import com.shifterwebapp.shifter.identity.domain.enums.CompanySize;
import lombok.Data;

@Data
public class PersonalizeUserReq {

    public String name;
    public String workPosition;
    private CompanySize companySize;

}
