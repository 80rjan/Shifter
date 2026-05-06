//package com.shifterwebapp.shifter.taxonomy.tag.service;
//
//import com.shifterwebapp.shifter.catalog.web.response.CourseSkillsAndInterestsView;
//import com.shifterwebapp.shifter.taxonomy.tag.dto.TagReqShort;
//
//import java.util.List;
//import java.util.Map;
//
//public interface ImplTagService {
//    List<Tag> processRawTags(List<String> rawTags, TagType type, LanguageCode language);
//    List<Tag> processExistingTags(List<TagReqShort> shortTags, TagType type, LanguageCode language);
//
//    Tag getById(Long id);
//
//    List<Tag> getTagsByIds(List<Long> ids);
//    List<Tag> getTagsByIdsAndType(List<Long> ids, TagType tagType);
//    List<Tag> getTagsByCourseIdAndType(Long courseId, TagType tagType);
//
//    List<String> getSkillsByCourseIdAndLanguage(Long courseId, LanguageCode language);
//    List<String> getTopicsByCourseIdAndLanguage(Long courseId, LanguageCode language);
//
//    List<String> getSkillsByUserId(Long userId, LanguageCode language);
//    List<String> getTopicsByUserId(Long userId, LanguageCode language);
//
//    Map<Long, List<String>> getSkillsByCourseIdsAndLanguage(List<Long> courseIds, LanguageCode language);
//    Map<Long, List<String>> getTopicsByCourseIdsAndLanguage(List<Long> courseIds, Language language);
//    Map<Long, CourseSkillsAndInterestsView> getTagsByCourseIdsAndLanguage(List<Long> courseIds, Language language);
//}
