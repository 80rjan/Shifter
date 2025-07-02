package com.shifterwebapp.shifter.auth;

import com.shifterwebapp.shifter.user.UserDto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthResponse {
    private String accessToken;
    private UserDto user;

}
