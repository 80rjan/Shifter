package com.shifterwebapp.shifter.learning.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {

    private Long id;

    private Integer rating;

    private String comment;

    private LocalDate reviewDate;

    private Integer enrollmentId;
}
