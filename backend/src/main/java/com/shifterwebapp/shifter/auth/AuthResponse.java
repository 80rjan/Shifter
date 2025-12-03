package com.shifterwebapp.shifter.auth;

import com.shifterwebapp.shifter.user.dto.UserDto;
import com.shifterwebapp.shifter.user.dto.UserDtoAuth;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthResponse {
    private String accessToken;
    private UserDtoAuth user;

}
