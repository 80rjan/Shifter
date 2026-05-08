package com.shifterwebapp.shifter.auth.web.response;

import com.shifterwebapp.shifter.identity.web.response.AuthenticatedUserResponse;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthResponse {
    private String accessToken;
    private AuthenticatedUserResponse user;

}
