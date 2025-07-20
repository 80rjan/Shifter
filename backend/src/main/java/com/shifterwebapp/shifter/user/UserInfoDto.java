package com.shifterwebapp.shifter.user;

import com.shifterwebapp.shifter.enums.CompanyType;
import lombok.Data;

@Data
public class UserInfoDto {

    public String name;
    public String workPosition;
    private CompanyType companyType;

}
