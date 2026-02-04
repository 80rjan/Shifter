package com.shifterwebapp.shifter.tag.repository;

import com.shifterwebapp.shifter.tag.TagTranslate;
import com.shifterwebapp.shifter.enums.TagType;
import com.shifterwebapp.shifter.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagTranslateRepository extends JpaRepository<TagTranslate, Long> {

    @Query("""
                select at
                from Course c
                join c.tags a
                join a.translations at
                where c.id = :courseId 
                and a.type = :type 
                and at.language = :language
            """)
    public List<TagTranslate> findByCourseIdAndTagTypeAndLanguage(
            @Param("courseId") Long courseId,
            @Param("type") TagType type,
            @Param("language") Language language
    );
}
