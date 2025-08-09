package com.shifterwebapp.shifter.course;

import com.shifterwebapp.shifter.enums.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    List<Course> findCoursesByTitle(String searchTitle);

    List<Course> findCoursesByIdNotIn(List<Long> courseIds);

    @Query("select c from Course c order by case when c.ratingCount = 0 then 0 else c.rating/c.ratingCount end desc")
    List<Course> findCoursesOrderedByRating();

    @Query("select c from Course c order by size(c.enrollments) desc")
    List<Course> findCoursesOrderedByPopularity();

    List<Course> findCoursesByDifficulty(Difficulty searchDifficulty);

    @Query("select c from Course c where c.price >= :floorPrice and c.price <= :ceilPrice")
    List<Course> findCoursesByPriceRange(@Param("floorPrice") Float floorPrice, @Param("ceilPrice") Float ceilPrice);

    List<Course> findCoursesBySkillsGainedIn(List<String> searchSkills);

    List<Course> findCoursesByDifficultyIn(List<Difficulty> searchDifficulties);

    @Query("""
                SELECT CASE WHEN COUNT(cl) > 0 THEN TRUE ELSE FALSE END
                FROM Course c
                JOIN c.courseContents cc
                JOIN cc.courseLectures cl
                WHERE c.id = :courseId
                  AND cl.contentFileName = :fileName
            """)
    Boolean lectureFileExistsInCourse(@Param("courseId") Long courseId,
                                      @Param("fileName") String fileName);


    @Query("select distinct c.topicsCovered from Course c")
    List<String> getCourseTopics();

    @Query("select distinct c.skillsGained from Course c")
    List<String> getCourseSkills();
}
