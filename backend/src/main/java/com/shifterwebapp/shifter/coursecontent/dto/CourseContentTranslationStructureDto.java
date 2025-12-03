package com.shifterwebapp.shifter.coursecontent.dto;

import com.shifterwebapp.shifter.courselecture.dto.CourseLectureTranslationStructureDto;

import java.util.List;

public record CourseContentTranslationStructureDto(Long id, Integer position, List<CourseLectureTranslationStructureDto> lectureList) {
}
