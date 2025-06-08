package com.shifterwebapp.shifter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Review {
    private Integer rating;
    private String comment;
    private Boolean canBeUsedAsTestimonial;
    private Date date;
}
