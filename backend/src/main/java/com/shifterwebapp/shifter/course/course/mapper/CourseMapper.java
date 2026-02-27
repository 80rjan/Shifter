package com.shifterwebapp.shifter.course.course.mapper;

import com.shifterwebapp.shifter.course.course.Course;
import com.shifterwebapp.shifter.course.coursetranslate.CourseTranslate;
import com.shifterwebapp.shifter.course.course.dto.*;
import com.shifterwebapp.shifter.course.coursetranslate.service.CourseTranslateService;
import com.shifterwebapp.shifter.course.courseversion.CourseVersion;
import com.shifterwebapp.shifter.course.courseversion.service.CourseVersionService;
import com.shifterwebapp.shifter.coursecontent.mapper.CourseContentMapper;
import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.enums.EnrollmentStatus;
import com.shifterwebapp.shifter.enums.Language;
import com.shifterwebapp.shifter.enums.TagType;
import com.shifterwebapp.shifter.review.Review;
import com.shifterwebapp.shifter.tag.Tag;
import com.shifterwebapp.shifter.tag.TagTranslate;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgress;
import com.shifterwebapp.shifter.utils.SlugService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper(componentModel = "spring", uses = {CourseContentMapper.class})
public abstract class CourseMapper {

    @Autowired
    protected SlugService slugService;

    @Autowired
    protected CourseTranslateService courseTranslateService;

    @Autowired
    protected CourseVersionService courseVersionService;

    @Autowired
    protected CourseContentMapper courseContentMapper;

    public abstract CourseDtoPreview toDtoPreview(Course course, @Context Language language,
                                                  @Context Map<Long, Double> avgRatingsMap,
                                                  @Context Map<Long, CourseVersion> courseVersionMap,
                                                  @Context Map<Long, CourseSkillsAndInterestsView> courseSkillsAndInterestsViewMap);

    public abstract List<CourseDtoPreview> toDtoPreview(List<Course> courses, @Context Language language,
                                                        @Context Map<Long, Double> avgRatingsMap,
                                                        @Context Map<Long, CourseVersion> courseVersionMap,
                                                        @Context Map<Long, CourseSkillsAndInterestsView> courseSkillsAndInterestsViewMap);

    public abstract CourseDtoDetail toDtoDetail(Course course, @Context Language language,
                                                @Context Map<Long, Double> avgRatingsMap,
                                                @Context Map<Long, CourseVersion> courseVersionMap,
                                                @Context Map<Long, CourseSkillsAndInterestsView> courseSkillsAndInterestsViewMap);

    public abstract CourseDtoPreviewEnrolled toDtoPreviewEnrolled(Course course, @Context Language language,
                                                                  @Context Map<Long, Enrollment> enrollmentMap,
                                                                  @Context Map<Long, List<UserCourseProgress>> progressMap,
                                                                  @Context Map<Long, Double> avgRatingsMap,
                                                                  @Context Map<Long, CourseVersion> courseVersionMap,
                                                                  @Context Map<Long, CourseSkillsAndInterestsView> courseSkillsAndInterestsViewMap);

    public abstract List<CourseDtoPreviewEnrolled> toDtoPreviewEnrolled(List<Course> courses, @Context Language language,
                                                                        @Context Map<Long, Enrollment> enrollmentMap,
                                                                        @Context Map<Long, List<UserCourseProgress>> progressMap,
                                                                        @Context Map<Long, Double> avgRatingsMap,
                                                                        @Context Map<Long, CourseVersion> courseVersionMap,
                                                                        @Context Map<Long, CourseSkillsAndInterestsView> courseSkillsAndInterestsViewMap);

    public abstract CourseDtoLearn toDtoLearn(Course course, @Context Language language, @Context Enrollment enrollment);

    public abstract List<CourseDtoLearn> toDtoLearn(List<Course> courses, @Context Language language, @Context Enrollment enrollment);

    public abstract CourseDtoFull toDtoFull(Course course, @Context Language language);

    public abstract List<CourseDtoFull> toDtoFull(List<Course> courses, @Context Language language);

    @AfterMapping
    protected void enrichPreview(@MappingTarget CourseDtoPreview dto,
                                 Course course,
                                 @Context Language language,
                                 @Context Map<Long, Double> avgRatingsMap,
                                 @Context Map<Long, CourseVersion> courseVersionMap,
                                 @Context Map<Long, CourseSkillsAndInterestsView> courseSkillsAndInterestsViewMap) {
        enrichCourseDtoPreview(dto, course, language, avgRatingsMap, courseVersionMap, courseSkillsAndInterestsViewMap);
    }

    @AfterMapping
    protected void enrichDetail(@MappingTarget CourseDtoDetail dto,
                                Course course,
                                @Context Language language,
                                @Context Map<Long, Double> avgRatingsMap,
                                @Context Map<Long, CourseVersion> courseVersionMap,
                                @Context Map<Long, CourseSkillsAndInterestsView> courseSkillsAndInterestsViewMap) {
        enrichCourseDtoPreview(dto, course, language, avgRatingsMap, courseVersionMap, courseSkillsAndInterestsViewMap);
        Optional<CourseTranslate> translationOpt = getTranslation(course, language);
        translationOpt.ifPresent(t -> {
            dto.setDescriptionShort(t.getDescriptionShort());
            dto.setDescription(t.getDescription());
            dto.setDescriptionLong(t.getDescriptionLong());
            dto.setWhatWillBeLearned(t.getWhatWillBeLearned());
        });

        dto.setCourseContents(courseVersionService.getActiveByCourseId(course.getId()).getCourseContents().stream()
                .map(cc -> courseContentMapper.toDtoPreview(cc, language))
                .toList());
    }

    @AfterMapping
    protected void enrichPreviewEnrolled(@MappingTarget CourseDtoPreviewEnrolled dto,
                                         Course course,
                                         @Context Language language,
                                         @Context Map<Long, Enrollment> enrollmentMap,
                                         @Context Map<Long, List<UserCourseProgress>> progressMap,
                                         @Context Map<Long, Double> avgRatingsMap,
                                         @Context Map<Long, CourseVersion> courseVersionMap,
                                         @Context Map<Long, CourseSkillsAndInterestsView> courseSkillsAndInterestsViewMap) {
        enrichCourseDtoPreview(dto, course, language, avgRatingsMap, courseVersionMap, courseSkillsAndInterestsViewMap);

        Enrollment enrollment = enrollmentMap.get(course.getId());
        if (enrollment == null) {
            dto.setLecturesFinishedCount(0);
            dto.setRating(null);
            dto.setIsFinished(false);
            return;
        }

        List<UserCourseProgress> userCourseProgress = progressMap.getOrDefault(enrollment.getId(), List.of());
        Review review = enrollment.getReview();
        Integer reviewRating = (review != null) ? review.getRating() : null;

        dto.setLecturesFinishedCount(userCourseProgress.size());
        dto.setRating(reviewRating);
        dto.setIsFinished(enrollment.getEnrollmentStatus() == EnrollmentStatus.COMPLETED);
    }

    @AfterMapping
    protected void enrichLearn(@MappingTarget CourseDtoLearn dto,
                               Course course,
                               @Context Language language,
                               @Context Enrollment enrollment) {
        Optional<CourseTranslate> translationOpt = getTranslation(course, language);
        translationOpt.ifPresent(t -> {
            dto.setTitleShort(t.getTitleShort());
            dto.setTitle(t.getTitle());
        });

        Review review = enrollment.getReview();
        Integer reviewRating = (review != null) ? review.getRating() : null;

        dto.setRating(reviewRating);
        dto.setIsFinished(enrollment.getEnrollmentStatus() == EnrollmentStatus.COMPLETED);

        dto.setCourseContents(courseVersionService.getActiveByCourseId(course.getId()).getCourseContents().stream()
                .map(cc -> courseContentMapper.toDtoLearn(cc, language, enrollment.getUser().getId()))
                .toList());
    }

    @AfterMapping
    protected void enrichFull(@MappingTarget CourseDtoFull dto,
                              Course course,
                              @Context Language language) {

        Optional<CourseTranslate> translationOpt = getTranslation(course, language);
        translationOpt.ifPresent(t -> {
            dto.setLanguage(language);
            dto.setTitleShort(t.getTitleShort());
            dto.setTitle(t.getTitle());
            dto.setDescriptionShort(t.getDescriptionShort());
            dto.setDescription(t.getDescription());
            dto.setDescriptionLong(t.getDescriptionLong());
            dto.setWhatWillBeLearned(t.getWhatWillBeLearned());
        });

        // Get ALL tags for this course at once (both skills and topics)
        List<Tag> allTags = course.getTags();

        // Encode skills
        List<String> encodedSkills = allTags.stream()
                .filter(tag -> tag.getType() == TagType.SKILL)
                .map(tag -> {
                    String value = tag.getTranslations().stream()
                            .filter(t -> t.getLanguage() == language)
                            .findFirst()
                            .map(TagTranslate::getValue)
                            .orElse("");
                    return tag.getId() + "_" + value;
                })
                .toList();

        // Encode topics
        List<String> encodedTopics = allTags.stream()
                .filter(tag -> tag.getType() == TagType.TOPIC)
                .map(tag -> {
                    String value = tag.getTranslations().stream()
                            .filter(t -> t.getLanguage() == language)
                            .findFirst()
                            .map(TagTranslate::getValue)
                            .orElse("");
                    return tag.getId() + "_" + value;
                })
                .toList();

        dto.setSkillsGained(encodedSkills);
        dto.setTopicsCovered(encodedTopics);

        dto.setCourseContents(courseVersionService.getActiveByCourseId(course.getId()).getCourseContents().stream()
                .map(cc -> courseContentMapper.toDtoFull(cc, language))
                .toList());
    }

    private Optional<CourseTranslate> getTranslation(Course course, Language language) {
        return course.getTranslations().stream()
                .filter(t -> t.getLanguage().equals(language))
                .findFirst();
    }


    protected <T extends CourseDtoPreview> void enrichCourseDtoPreview(
            T dto,
            Course course,
            Language language,
            Map<Long, Double> avgRatingsMap,
            Map<Long, CourseVersion> courseVersionMap,
            Map<Long, CourseSkillsAndInterestsView> courseSkillsAndInterestsViewMap
    ) {
        Double rating = avgRatingsMap.getOrDefault(course.getId(), 0.0);
        dto.setAverageRating(rating != null ? rating : 0.0);

        CourseVersion courseVersion = courseVersionMap.get(course.getId());
        int contentCount = 0;
        int lectureCount = 0;
        if (courseVersion != null && courseVersion.getCourseContents() != null) {
            contentCount = courseVersion.getCourseContents().size();
            lectureCount = courseVersion.getCourseContents().stream()
                    .mapToInt(content -> content.getCourseLectures() != null
                            ? content.getCourseLectures().size() : 0)
                    .sum();
        }

        dto.setCourseContentCount(contentCount);
        dto.setCourseLectureCount(lectureCount);

        dto.setSkillsGained(courseSkillsAndInterestsViewMap.getOrDefault(course.getId(), new CourseSkillsAndInterestsView()).getSkills());
        dto.setTopicsCovered(courseSkillsAndInterestsViewMap.getOrDefault(course.getId(), new CourseSkillsAndInterestsView()).getInterests());

        List<Language> courseLanguages = course.getTranslations().stream().map(CourseTranslate::getLanguage).toList();
        dto.setTranslatedLanguages(courseLanguages);

        Optional<CourseTranslate> translationOpt = getTranslation(course, language);
        translationOpt.ifPresent(t -> {
            dto.setTitleShort(t.getTitleShort());
            dto.setTitle(t.getTitle());
            dto.setUrlSlug(slugService.slugify(t.getTitleShort()));
        });
    }
}