package com.shifterwebapp.shifter.courselecture.service;

import com.shifterwebapp.shifter.courselecture.CourseLectureTranslate;
import com.shifterwebapp.shifter.enums.Language;

public interface ImplCourseLectureService {

    CourseLectureTranslate getByCourseLectureIdAndLanguage(Long lectureId, Language language);
}
