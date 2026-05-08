package com.shifterwebapp.shifter.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContactRequest {
    public String subject;
    public String text;
}
