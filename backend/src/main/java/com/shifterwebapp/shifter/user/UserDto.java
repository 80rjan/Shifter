package com.shifterwebapp.shifter.user;

import com.shifterwebapp.shifter.payment.PaymentDto;
import com.shifterwebapp.shifter.user.enums.CompanyType;
import com.shifterwebapp.shifter.user.enums.Interests;
import com.shifterwebapp.shifter.user.enums.Skills;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String email;

    private String name;

    private CompanyType companyType;

    private String workPosition;

    private List<Interests> interests;

    private List<Skills> skills;

    private List<Skills> skillGap;

    private Integer points;

    private List<Integer> favoriteCourses;

    private List<PaymentDto> payments;
}



