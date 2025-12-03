package com.shifterwebapp.shifter.attribute.repository;

import com.shifterwebapp.shifter.attribute.Attribute;
import com.shifterwebapp.shifter.enums.AttributeType;
import com.shifterwebapp.shifter.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    @Query("select a from Course c join c.attributes a where c.id = :courseId and a.type = :type")
    List<Attribute> findAllByCourseIdAndType(@Param("courseId") Long courseId, @Param("type") AttributeType type);

    @Query("select a from Attribute a where a.type = :type and a.id in :idList")
    List<Attribute> findAllByIdAndType(@Param("idList") List<Long> idList, @Param("type")AttributeType type);

    @Query("select a from Attribute a where a.type = :type and a.id in :id")
    Optional<Attribute> findByIdAndType(@Param("id") Long id, @Param("type") AttributeType type);

    @Query("select a from Attribute a join a.translations at where a.type = :type and at.value = :value and at.language = :language")
    Optional<Attribute> findByTypeAndTranslationValueAndLanguage(@Param("type") AttributeType type, @Param("value") String value, @Param("language") Language language);

    @Query("""
                select a
                from Course c
                join c.attributes a
                join a.translations at
                where c.id = :courseId 
                and a.type = :type 
                and at.language = :language
            """)
    public List<Attribute> findByCourseIdAndTypeAndLanguage(
            @Param("courseId") Long courseId,
            @Param("type") AttributeType type,
            @Param("language") Language language
    );

    @Query("""
        select at.value
        from Course c
        join c.attributes a
        join a.translations at
        where c.id = :courseId and at.language = :language and a.type = :type
    """)
    public List<String> getAttributeValueByCourseId(@Param("courseId") Long courseId, @Param("type") AttributeType type, @Param("language") Language language);

    @Query("""
        select at.value
        from User u
        join u.attributes a
        join a.translations at
        where u.id = :userId and at.language = :language and a.type = :type
    """)
    public List<String> getAttributeValueByUserId(@Param("userId") Long userId, @Param("type") AttributeType type, @Param("language") Language language);

}
