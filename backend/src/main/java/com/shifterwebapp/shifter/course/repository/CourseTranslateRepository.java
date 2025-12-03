package com.shifterwebapp.shifter.course.repository;

import com.shifterwebapp.shifter.attribute.Attribute;
import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.course.CourseTranslate;
import com.shifterwebapp.shifter.enums.Difficulty;
import com.shifterwebapp.shifter.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseTranslateRepository extends JpaRepository<CourseTranslate, Long> {

    public CourseTranslate getCourseTranslateByCourseIdAndLanguage(Long courseId, Language language);

    @Query("select ct.language from CourseTranslate ct where ct.course.id = :courseId")
    public List<Language> getCourseLanguages(@Param("courseId") Long courseId);
}
