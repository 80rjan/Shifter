package com.shifterwebapp.shifter.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RangeDto {
    private Double floor;
    private Double ceil;
}
