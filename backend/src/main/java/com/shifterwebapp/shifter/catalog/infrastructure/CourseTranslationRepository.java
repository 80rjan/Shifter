//package com.shifterwebapp.shifter.course.repositories;
//
//import com.shifterwebapp.shifter.catalog.domain.CourseTranslation;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface CourseTranslateRepository extends JpaRepository<CourseTranslation, Long> {
//
//    CourseTranslation findByCourseIdAndLanguage(Long courseId, LanguageCode language);
//
//    @Query("select ct.language from CourseTranslation ct where ct.course.id = :courseId")
//    List<Language> findByCourseId(@Param("courseId") Long courseId);
//}
