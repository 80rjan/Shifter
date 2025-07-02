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

    List<Course> findCoursesByTopic(String searchTopic);

    List<Course> findCoursesByDifficulty(Difficulty searchDifficulty);

    @Query("select c from Course c where c.price >= :floorPrice and c.price <= :ceilPrice")
    List<Course> findCoursesByPriceRange(@Param("floorPrice") Float floorPrice,@Param("ceilPrice") Float ceilPrice);

    @Query("select c from Course c where c.durationHours >= :floorDuration and c.durationHours <= :ceilDuration")
    List<Course> findCoursesByDurationHoursRange(@Param("floorDuration") Float floorDuration, @Param("ceilDuration") Float ceilDuration);

    List<Course> findCoursesBySkillsGainedIn(List<Skills> searchSkills);

    List<Course> findCoursesByDifficultyIn(List<Difficulty> searchDifficulties);

    @Query("select distinct c.whatWillBeLearned from Course c")
    List<Interests> getCourseTopics();

    @Query("select distinct c.skillsGained from Course c")
    List<Skills> getCourseSkills();
}
