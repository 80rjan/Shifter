package com.shifterwebapp.shifter.tag.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.course.course.dto.CourseSkillsAndInterestsView;
import com.shifterwebapp.shifter.course.course.projection.CourseTagProjection;
import com.shifterwebapp.shifter.tag.Tag;
import com.shifterwebapp.shifter.tag.TagTranslate;
import com.shifterwebapp.shifter.tag.dto.TagReqShort;
import com.shifterwebapp.shifter.tag.repository.TagRepository;
import com.shifterwebapp.shifter.enums.TagType;
import com.shifterwebapp.shifter.enums.Language;
import com.shifterwebapp.shifter.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService implements ImplTagService {


    private final TagRepository tagRepository;
    private final Validate validate;

    @Override
    @Transactional
    public List<Tag> processRawTags(List<String> rawTags, TagType type, Language language) {
        return rawTags.stream()
                .distinct()
                .map(String::toUpperCase)
                .map(value -> {
                    Optional<Tag> existingTagOptional =
                            tagRepository.findByTypeAndTranslationValueAndLanguage(type, value, language);

                    if (existingTagOptional.isPresent()) {
                        return existingTagOptional.get();
                    }

                    // Create new Tag and its Translation
                    Tag tag = Tag.builder()
                            .type(type)
                            .build();

                    TagTranslate tagTranslate = TagTranslate.builder()
                            .language(language)
                            .value(value)
                            .tag(tag)
                            .build();

                    tag.setTranslations(List.of(tagTranslate));

                    return tagRepository.save(tag);
                })
                .toList();
    }

    @Override
    @Transactional
    public List<Tag> processExistingTags(List<TagReqShort> shortTags, TagType type, Language language) {
        List<Tag> tags = new ArrayList<>();

        for (TagReqShort req : shortTags) {
            Long id = req.id();
            String value = req.value();

            validate.validateTagExists(id);
            Tag tag = tagRepository.findByIdAndType(id, type).orElseThrow(
                    () -> new ResourceNotFoundException("Tag with id " + id + " is not of type " + type)
            );

            TagTranslate tagTranslate = TagTranslate.builder()
                    .value(value)
                    .language(language)
                    .tag(tag)
                    .build();

            tag.getTranslations().add(tagTranslate);
            tags.add(tag);
        }

        return tagRepository.saveAll(tags);
    }

    @Override
    @Transactional(readOnly = true)
    public Tag getById(Long id) {
        validate.validateTagExists(id);
        return tagRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> getTagsByIds(List<Long> ids) {
        return tagRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> getTagsByIdsAndType(List<Long> ids, TagType tagType) {
        return tagRepository.findByIdInAndType(ids, tagType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> getTagsByCourseIdAndType(Long courseId, TagType tagType) {
        return tagRepository.findByCourseIdAndType(courseId, tagType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getSkillsByCourseIdAndLanguage(Long courseId, Language language) {
        return tagRepository.findTagValueByCourseIdAndTypeAndLanguage(courseId, TagType.SKILL, language);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getTopicsByCourseIdAndLanguage(Long courseId, Language language) {
        return tagRepository.findTagValueByCourseIdAndTypeAndLanguage(courseId, TagType.TOPIC, language);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getSkillsByUserId(Long userId, Language language) {
        return tagRepository.findTagValueByUserIdAndTypeAndLanguage(userId, TagType.SKILL, language);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getTopicsByUserId(Long userId, Language language) {
        return tagRepository.findTagValueByUserIdAndTypeAndLanguage(userId, TagType.TOPIC, language);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, List<String>> getSkillsByCourseIdsAndLanguage(List<Long> courseIds, Language language) {
        return tagRepository.findByCourseIdInAndTypeAndLanguage(courseIds, TagType.SKILL, language)
                .stream()
                .collect(Collectors.groupingBy(
                        CourseTagProjection::getCourseId,
                        Collectors.mapping(
                                CourseTagProjection::getTagValue,
                                Collectors.toList()
                        )
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, List<String>> getTopicsByCourseIdsAndLanguage(List<Long> courseIds, Language language) {
        return tagRepository.findByCourseIdInAndTypeAndLanguage(courseIds, TagType.TOPIC, language)
                .stream()
                .collect(Collectors.groupingBy(
                        CourseTagProjection::getCourseId,
                        Collectors.mapping(
                                CourseTagProjection::getTagValue,
                                Collectors.toList()
                        )
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, CourseSkillsAndInterestsView> getTagsByCourseIdsAndLanguage(
            List<Long> courseIds,
            Language language
    ) {
        List<CourseTagProjection> rows =
                tagRepository.findByCourseIdInAndLanguage(courseIds, language);

        Map<Long, List<String>> skillsMap = new HashMap<>();
        Map<Long, List<String>> interestsMap = new HashMap<>();

        // single pass – fast
        for (CourseTagProjection row : rows) {
            Map<Long, List<String>> target =
                    row.getTagType() == TagType.SKILL ? skillsMap : interestsMap;

            target
                    .computeIfAbsent(row.getCourseId(), k -> new ArrayList<>())
                    .add(row.getTagValue());
        }

        return courseIds.stream()
                .collect(Collectors.toMap(
                        courseId -> courseId,
                        courseId -> new CourseSkillsAndInterestsView(
                                courseId,
                                skillsMap.getOrDefault(courseId, Collections.emptyList()),
                                interestsMap.getOrDefault(courseId, Collections.emptyList())
                        )
                ));
    }
}
