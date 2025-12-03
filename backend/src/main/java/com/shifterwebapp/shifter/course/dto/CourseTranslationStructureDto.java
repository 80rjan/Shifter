package com.shifterwebapp.shifter.course.dto;

import com.shifterwebapp.shifter.coursecontent.dto.CourseContentTranslationStructureDto;
import com.shifterwebapp.shifter.enums.Difficulty;

import java.util.List;

public record CourseTranslationStructureDto(
        Long id,
        String imageUrl,
        String color,
        Difficulty difficulty,
        Integer durationMinutes,
        Double price,
        List<CourseContentTranslationStructureDto> contentList
) {
}
