package com.shifterwebapp.shifter.identity.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoAuth {

    private Long id;

    private String email;

    private String name;

}
