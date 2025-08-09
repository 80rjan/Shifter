package com.shifterwebapp.shifter.courselecture.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.courselecture.CourseLectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseLectureService implements ImplCourseLectureService{

    private final Validate validate;
    private final CourseLectureRepository courseLectureRepository;

    @Override
    public String getContentType(String fileName, Long courseId, Long lectureId) {
        validate.validateCourseExists(courseId);
        return courseLectureRepository.getContentType(fileName, courseId, lectureId);
    }
}
