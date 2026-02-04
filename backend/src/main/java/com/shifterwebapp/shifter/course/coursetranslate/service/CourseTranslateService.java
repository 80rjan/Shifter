package com.shifterwebapp.shifter.course.coursetranslate.service;

import com.shifterwebapp.shifter.course.coursetranslate.repository.CourseTranslateRepository;
import com.shifterwebapp.shifter.enums.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseTranslateService implements ImplCourseTranslateService {

    protected CourseTranslateRepository courseTranslateRepository;

    @Override
    public List<Language> getCourseTranslatedLanguages(Long courseId) {
        return courseTranslateRepository.findByCourseId(courseId);
    }
}
