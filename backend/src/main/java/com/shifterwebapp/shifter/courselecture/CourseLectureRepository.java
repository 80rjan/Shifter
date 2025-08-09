package com.shifterwebapp.shifter.courselecture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseLectureRepository extends JpaRepository<CourseLecture, Long> {

    @Query("select cl.contentType from CourseLecture cl where cl.id = :lectureId and cl.contentFileName = :fileName and cl.courseContent.course.id = :courseId")
    String getContentType(@Param("fileName") String fileName,@Param("courseId") Long courseId, @Param("lectureId") Long lectureId);
}
