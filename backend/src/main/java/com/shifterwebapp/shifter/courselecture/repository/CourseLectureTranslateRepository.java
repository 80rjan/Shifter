package com.shifterwebapp.shifter.courselecture.repository;

import com.shifterwebapp.shifter.courselecture.CourseLectureTranslate;
import com.shifterwebapp.shifter.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseLectureTranslateRepository extends JpaRepository<CourseLectureTranslate, Long> {
    CourseLectureTranslate findByIdAndLanguage(Long lectureId, Language language);
}
