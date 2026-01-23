package com.shifterwebapp.shifter.courselecture.repository;

import com.shifterwebapp.shifter.courselecture.CourseLecture;
import com.shifterwebapp.shifter.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseLectureRepository extends JpaRepository<CourseLecture, Long> {

    @Query("""
            SELECT cl.contentType
            FROM CourseLecture cl
            JOIN cl.translations clt
            WHERE cl.id = :lectureId
             AND clt.contentFileName = :fileName
             AND clt.language = :language
            """)
    String getContentType(@Param("fileName") String fileName, @Param("lectureId") Long lectureId, @Param("language") Language language);

    @Query("""
        SELECT clt.contentFileName
        FROM CourseLecture cl
        JOIN cl.translations clt
        WHERE cl.id = :lectureId
        AND clt.language = :language
    """)
    String getFileName(@Param("lectureId") Long lectureId, @Param("language") Language language);
}
