package com.shifterwebapp.shifter.tag.service;

import com.shifterwebapp.shifter.course.course.dto.CourseSkillsAndInterestsView;
import com.shifterwebapp.shifter.tag.Tag;
import com.shifterwebapp.shifter.tag.dto.TagReqShort;
import com.shifterwebapp.shifter.enums.TagType;
import com.shifterwebapp.shifter.enums.Language;

import java.util.List;
import java.util.Map;

public interface ImplTagService {
    List<Tag> processRawTags(List<String> rawTags, TagType type, Language language);
    List<Tag> processExistingTags(List<TagReqShort> shortTags, TagType type, Language language);

    Tag getById(Long id);

    List<Tag> getTagsByIds(List<Long> ids);
    List<Tag> getTagsByIdsAndType(List<Long> ids, TagType tagType);
    List<Tag> getTagsByCourseIdAndType(Long courseId, TagType tagType);

    List<String> getSkillsByCourseIdAndLanguage(Long courseId, Language language);
    List<String> getTopicsByCourseIdAndLanguage(Long courseId, Language language);

    List<String> getSkillsByUserId(Long userId, Language language);
    List<String> getTopicsByUserId(Long userId, Language language);

    Map<Long, List<String>> getSkillsByCourseIdsAndLanguage(List<Long> courseIds, Language language);
    Map<Long, List<String>> getTopicsByCourseIdsAndLanguage(List<Long> courseIds, Language language);
    Map<Long, CourseSkillsAndInterestsView> getTagsByCourseIdsAndLanguage(List<Long> courseIds, Language language);
}
