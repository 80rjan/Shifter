package com.shifterwebapp.shifter.course.repository;

import com.shifterwebapp.shifter.attribute.Attribute;
import com.shifterwebapp.shifter.attribute.AttributeTranslate;
import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.enums.Difficulty;
import com.shifterwebapp.shifter.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {

    @Query("select c from Course c join c.courseTranslates ct where ct.language = :language")
    List<Course> findCoursesByLanguage(@Param("language") Language language);

    @Query("select c from Course c join c.courseTranslates ct where ct.language = :language and c.id not in (:courseIds)")
    List<Course> findCoursesByIdNotInAndLanguage(@Param("courseIds") List<Long> courseIds, @Param("language") Language language);

    List<Course> findCoursesByIdNotIn(List<Long> courseIds);

    @Query("""
                SELECT c
                FROM Course c
                LEFT JOIN c.enrollments e
                LEFT JOIN e.review r
                GROUP BY c
                ORDER BY COALESCE(AVG(r.rating * 1.0), 0) DESC
            """)
    List<Course> findCoursesOrderedByRating();

    @Query("select c from Course c order by size(c.enrollments) desc")
    List<Course> findCoursesOrderedByPopularity();

    List<Course> findCoursesByDifficulty(Difficulty searchDifficulty);

    @Query("select c from Course c where c.price >= :floorPrice and c.price <= :ceilPrice")
    List<Course> findCoursesByPriceRange(@Param("floorPrice") Float floorPrice, @Param("ceilPrice") Float ceilPrice);

    List<Course> findCoursesByDifficultyIn(List<Difficulty> searchDifficulties);

    @Query("select cl.courseContent.course from CourseLecture cl where cl.id = :lectureId")
    Course findByLectureId(@Param("lectureId") Long lectureId);

    @Query("""
                SELECT CASE WHEN COUNT(cl) > 0 THEN TRUE ELSE FALSE END
                FROM Course c
                JOIN c.courseContents cc
                JOIN cc.courseLectures cl
                JOIN cl.courseLectureTranslates clt
                WHERE c.id = :courseId
                  AND clt.contentFileName = :fileName
            """)
    Boolean lectureFileExistsInCourse(@Param("courseId") Long courseId,
                                      @Param("fileName") String fileName);

    @Query("select case when count(ct) > 0 then true else false end from CourseTranslate ct where ct.course.id = :courseId and ct.language = :language")
    Boolean courseHasBeenTranslated(@Param("courseId") Long courseId, @Param("language") Language language);

    @Query("""
                select at 
                from Course c
                join c.attributes a
                join a.translations at
                where  
                    a.type = com.shifterwebapp.shifter.enums.AttributeType.TOPIC
                    and at.language = :language
            """)
    List<AttributeTranslate> getCourseTopics(@Param("language") Language language);

    @Query("""
                select at 
                from Course c
                join c.attributes a
                join a.translations at
                where  
                    a.type = com.shifterwebapp.shifter.enums.AttributeType.SKILL
                    and at.language = :language
            """)
    List<AttributeTranslate> getCourseSkills(@Param("language") Language language);

    @Query("""
        select ct.titleShort
        from Course c
        join c.courseTranslates ct
        where c.id = :courseId
        and ct.language = com.shifterwebapp.shifter.enums.Language.EN
    """)
    String getEnCourseTitle(@Param("courseId") Long courseId);
}
