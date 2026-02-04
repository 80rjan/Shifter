package com.shifterwebapp.shifter.course.coursetranslate.service;

import com.shifterwebapp.shifter.enums.Language;

import java.util.List;

public interface ImplCourseTranslateService {
    List<Language> getCourseTranslatedLanguages(Long courseId);
}
