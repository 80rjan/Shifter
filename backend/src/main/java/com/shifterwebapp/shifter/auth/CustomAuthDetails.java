package com.shifterwebapp.shifter.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomAuthDetails {
    private final Long userId;
    private final Object webDetails;

    public CustomAuthDetails(Long userId, Object webDetails) {
        this.userId = userId;
        this.webDetails = webDetails;
    }

}
