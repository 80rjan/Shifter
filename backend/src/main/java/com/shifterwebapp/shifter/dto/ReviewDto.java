package com.shifterwebapp.shifter.dto;

import com.shifterwebapp.shifter.model.Enrollment;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Integer rating;

    private String comment;

    private Boolean canBeUsedAsTestimonial;

    private Date date;

    private Integer enrollmentId;
}
