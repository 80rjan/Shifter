package com.shifterwebapp.shifter.auth.web.request;

import lombok.Data;

@Data
public class TokenRefreshDto {

    String refreshToken;
}
