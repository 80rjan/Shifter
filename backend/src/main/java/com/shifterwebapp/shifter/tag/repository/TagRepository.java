package com.shifterwebapp.shifter.tag.repository;

import com.shifterwebapp.shifter.course.course.projection.CourseTagProjection;
import com.shifterwebapp.shifter.tag.Tag;
import com.shifterwebapp.shifter.enums.TagType;
import com.shifterwebapp.shifter.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("select a from Course c join c.tags a where c.id = :courseId and a.type = :type")
    List<Tag> findByCourseIdAndType(@Param("courseId") Long courseId, @Param("type") TagType type);

    @Query("select a from Tag a where a.type = :type and a.id in :idList")
    List<Tag> findByIdInAndType(@Param("idList") List<Long> idList, @Param("type") TagType type);

    @Query("select a from Tag a where a.type = :type and a.id in :id")
    Optional<Tag> findByIdAndType(@Param("id") Long id, @Param("type") TagType type);

    @Query("select a from Tag a join a.translations at where a.type = :type and at.value = :value and at.language = :language")
    Optional<Tag> findByTypeAndTranslationValueAndLanguage(@Param("type") TagType type, @Param("value") String value, @Param("language") Language language);

    @Query("""
                select a
                from Course c
                join c.tags a
                join a.translations at
                where c.id = :courseId
                and a.type = :type 
                and at.language = :language
            """)
    List<Tag> findByCourseIdAndTypeAndLanguage(
            @Param("courseId") Long courseId,
            @Param("type") TagType type,
            @Param("language") Language language
    );

    @Query("""
        select at.value
        from Course c
        join c.tags a
        join a.translations at
        where c.id = :courseId and at.language = :language and a.type = :type
    """)
    List<String> findTagValueByCourseIdAndTypeAndLanguage(@Param("courseId") Long courseId, @Param("type") TagType type, @Param("language") Language language);

    @Query("""
        select at.value
        from User u
        join u.tags a
        join a.translations at
        where u.id = :userId and at.language = :language and a.type = :type
    """)
    List<String> findTagValueByUserIdAndTypeAndLanguage(@Param("userId") Long userId, @Param("type") TagType type, @Param("language") Language language);

    @Query("""
        select c.id as courseId, tt.value as tagValue, t.type as tagType
        from Course c
        join c.tags t
        join t.translations tt
        where c.id in :courseIds and t.type = :type and tt.language = :language
    """)
    List<CourseTagProjection> findByCourseIdInAndTypeAndLanguage(@Param("courseIds") List<Long> courseIds, @Param("type") TagType type, @Param("language") Language language);

    @Query("""
        select c.id as courseId, tt.value as tagValue, t.type as tagType
        from Course c
        join c.tags t
        join t.translations tt
        where c.id in :courseIds and tt.language = :language
    """)
    List<CourseTagProjection> findByCourseIdInAndLanguage(@Param("courseIds") List<Long> courseIds, @Param("language") Language language);
}
