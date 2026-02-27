package com.shifterwebapp.shifter.account.expert.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpertDto {

    private Long id;

    private String email;

    private String name;
}
