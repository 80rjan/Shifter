package com.shifterwebapp.shifter.infrastructure.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MetaInfo {
    private Integer contentPosition;
    private Integer lecturePosition;
    private Long courseId;
}