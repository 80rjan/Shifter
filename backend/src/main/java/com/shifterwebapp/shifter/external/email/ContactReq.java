package com.shifterwebapp.shifter.external.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContactReq {
    public String subject;
    public String text;
}
