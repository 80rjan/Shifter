package com.shifterwebapp.shifter.course;

import com.shifterwebapp.shifter.enums.Difficulty;
import com.shifterwebapp.shifter.enums.Interests;
import com.shifterwebapp.shifter.enums.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    List<Course> findCoursesByTitle(String searchTitle);

    @Query("select c from Course c order by c.rating/c.ratingCount desc")
    List<Course> findCoursesOrderedByRating();

    @Query("select c from Course c order by size(c.enrollments) desc")
    List<Course> findCoursesOrderedByPopularity();

    List<Course> findCoursesByDifficulty(Difficulty searchDifficulty);

    @Query("select c from Course c where c.price >= :floorPrice and c.price <= :ceilPrice")
    List<Course> findCoursesByPriceRange(@Param("floorPrice") Float floorPrice,@Param("ceilPrice") Float ceilPrice);

    List<Course> findCoursesBySkillsGainedIn(List<Skills> searchSkills);

    List<Course> findCoursesByDifficultyIn(List<Difficulty> searchDifficulties);

    @Query("select distinct c.topicsCovered from Course c")
    List<Interests> getCourseTopics();

    @Query("select distinct c.skillsGained from Course c")
    List<Skills> getCourseSkills();
}
