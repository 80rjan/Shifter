package com.shifterwebapp.shifter.course.coursetranslate.repository;

import com.shifterwebapp.shifter.course.coursetranslate.CourseTranslate;
import com.shifterwebapp.shifter.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseTranslateRepository extends JpaRepository<CourseTranslate, Long> {

    CourseTranslate findByCourseIdAndLanguage(Long courseId, Language language);

    @Query("select ct.language from CourseTranslate ct where ct.course.id = :courseId")
    List<Language> findByCourseId(@Param("courseId") Long courseId);
}
