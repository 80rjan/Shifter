package com.shifterwebapp.shifter.identity.web.response;

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
