package com.shifterwebapp.shifter.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private Long id;

    private Integer rating;

    private String comment;

    private Boolean canBeUsedAsTestimonial;

    private Date date;

    private Integer enrollmentId;
}
