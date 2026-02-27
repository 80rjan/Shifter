package com.shifterwebapp.shifter.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomAuthDetails {
    private final Long userId;
    private final String role;
    private final Object webDetails;

    public CustomAuthDetails(Long userId, String role, Object webDetails) {
        this.userId = userId;
        this.role = role;
        this.webDetails = webDetails;
    }

}
