package com.shifterwebapp.shifter.learning.web.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureProgressResponse {

    private Long id;

    private boolean completed;

    private LocalDateTime completedAt;
}
