package com.shifterwebapp.shifter.attribute.repository;

import com.shifterwebapp.shifter.attribute.AttributeTranslate;
import com.shifterwebapp.shifter.enums.AttributeType;
import com.shifterwebapp.shifter.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttributeTranslateRepository extends JpaRepository<AttributeTranslate, Long> {

    @Query("""
                select at
                from Course c
                join c.attributes a
                join a.translations at
                where c.id = :courseId 
                and a.type = :type 
                and at.language = :language
            """)
    public List<AttributeTranslate> findByCourseIdAndAttributeTypeAndLanguage(
            @Param("courseId") Long courseId,
            @Param("type") AttributeType type,
            @Param("language") Language language
    );
}
