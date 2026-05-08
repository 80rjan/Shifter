//package com.shifterwebapp.shifter.course.mappers;
//
//import com.shifterwebapp.shifter.catalog.web.response.*;
//import com.shifterwebapp.shifter.catalog.domain.Course;
//import com.shifterwebapp.shifter.catalog.domain.CourseTranslation;
//import com.shifterwebapp.shifter.catalog.application.CourseTranslateService;
//import com.shifterwebapp.shifter.catalog.domain.CourseVersion;
//import com.shifterwebapp.shifter.catalog.application.CourseVersionService;
//import com.shifterwebapp.shifter.learning.domain.Enrollment;
//import com.shifterwebapp.shifter.learning.domain.enums.EnrollmentStatus;
//import com.shifterwebapp.shifter.learning.Review;
//import com.shifterwebapp.shifter.catalog.domain.LectureProgress;
//import com.shifterwebapp.shifter.shared.util.SlugService;
//import org.mapstruct.*;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Mapper(componentModel = "spring", uses = {CourseModuleMapper.class})
//public abstract class CourseMapper {
//
//    @Autowired
//    protected SlugService slugService;
//
//    @Autowired
//    protected CourseTranslateService courseTranslateService;
//
//    @Autowired
//    protected CourseVersionService courseVersionService;
//
//    @Autowired
//    protected CourseModuleMapper courseModuleMapper;
//
//    public abstract CoursePreviewResponse toDtoPreview(Course course, @Context LanguageCode language,
//                                                  @Context Map<Long, Double> avgRatingsMap,
//                                                  @Context Map<Long, CourseVersion> courseVersionMap,
//                                                  @Context Map<Long, CourseSkillsAndInterestsView> courseSkillsAndInterestsViewMap);
//
//    public abstract List<CoursePreviewResponse> toDtoPreview(List<Course> courses, @Context LanguageCode language,
//                                                        @Context Map<Long, Double> avgRatingsMap,
//                                                        @Context Map<Long, CourseVersion> courseVersionMap,
//                                                        @Context Map<Long, CourseSkillsAndInterestsView> courseSkillsAndInterestsViewMap);
//
//    public abstract CourseDtoDetail toDtoDetail(Course course, @Context LanguageCode language,
//                                                @Context Map<Long, Double> avgRatingsMap,
//                                                @Context Map<Long, CourseVersion> courseVersionMap,
//                                                @Context Map<Long, CourseSkillsAndInterestsView> courseSkillsAndInterestsViewMap);
//
//    public abstract CourseDtoPreviewEnrolled toDtoPreviewEnrolled(Course course, @Context LanguageCode language,
//                                                                  @Context Map<Long, Enrollment> enrollmentMap,
//                                                                  @Context Map<Long, List<LectureProgress>> progressMap,
//                                                                  @Context Map<Long, Double> avgRatingsMap,
//                                                                  @Context Map<Long, CourseVersion> courseVersionMap,
//                                                                  @Context Map<Long, CourseSkillsAndInterestsView> courseSkillsAndInterestsViewMap);
//
//    public abstract List<CourseDtoPreviewEnrolled> toDtoPreviewEnrolled(List<Course> courses, @Context LanguageCode language,
//                                                                        @Context Map<Long, Enrollment> enrollmentMap,
//                                                                        @Context Map<Long, List<LectureProgress>> progressMap,
//                                                                        @Context Map<Long, Double> avgRatingsMap,
//                                                                        @Context Map<Long, CourseVersion> courseVersionMap,
//                                                                        @Context Map<Long, CourseSkillsAndInterestsView> courseSkillsAndInterestsViewMap);
//
//    public abstract CourseLearningResponse toDtoLearn(Course course, @Context LanguageCode language, @Context Enrollment enrollment);
//
//    public abstract List<CourseLearningResponse> toDtoLearn(List<Course> courses, @Context LanguageCode language, @Context Enrollment enrollment);
//
//    public abstract CourseDtoFull toDtoFull(Course course, @Context LanguageCode language);
//
//    public abstract List<CourseDtoFull> toDtoFull(List<Course> courses, @Context LanguageCode language);
//
//    @AfterMapping
//    protected void enrichPreview(@MappingTarget CoursePreviewResponse dto,
//                                 Course course,
//                                 @Context LanguageCode language,
//                                 @Context Map<Long, Double> avgRatingsMap,
//                                 @Context Map<Long, CourseVersion> courseVersionMap,
//                                 @Context Map<Long, CourseSkillsAndInterestsView> courseSkillsAndInterestsViewMap) {
//        enrichCourseDtoPreview(dto, course, language, avgRatingsMap, courseVersionMap, courseSkillsAndInterestsViewMap);
//    }
//
//    @AfterMapping
//    protected void enrichDetail(@MappingTarget CourseDtoDetail dto,
//                                Course course,
//                                @Context LanguageCode language,
//                                @Context Map<Long, Double> avgRatingsMap,
//                                @Context Map<Long, CourseVersion> courseVersionMap,
//                                @Context Map<Long, CourseSkillsAndInterestsView> courseSkillsAndInterestsViewMap) {
//        enrichCourseDtoPreview(dto, course, language, avgRatingsMap, courseVersionMap, courseSkillsAndInterestsViewMap);
//        Optional<CourseTranslation> translationOpt = getTranslation(course, language);
//        translationOpt.ifPresent(t -> {
//            dto.setDescriptionShort(t.getDescriptionShort());
//            dto.setDescription(t.getDescription());
//            dto.setDescriptionLong(t.getDescriptionLong());
//            dto.setWhatWillBeLearned(t.getWhatWillBeLearned());
//        });
//
//        dto.setCourseContents(courseVersionService.getActiveByCourseId(course.getId()).getCourseModules().stream()
//                .map(cc -> courseModuleMapper.toDtoPreview(cc, language))
//                .toList());
//    }
//
//    @AfterMapping
//    protected void enrichPreviewEnrolled(@MappingTarget CourseDtoPreviewEnrolled dto,
//                                         Course course,
//                                         @Context LanguageCode language,
//                                         @Context Map<Long, Enrollment> enrollmentMap,
//                                         @Context Map<Long, List<LectureProgress>> progressMap,
//                                         @Context Map<Long, Double> avgRatingsMap,
//                                         @Context Map<Long, CourseVersion> courseVersionMap,
//                                         @Context Map<Long, CourseSkillsAndInterestsView> courseSkillsAndInterestsViewMap) {
//        enrichCourseDtoPreview(dto, course, language, avgRatingsMap, courseVersionMap, courseSkillsAndInterestsViewMap);
//
//        Enrollment enrollment = enrollmentMap.get(course.getId());
//        if (enrollment == null) {
//            dto.setLecturesFinishedCount(0);
//            dto.setRating(null);
//            dto.setIsFinished(false);
//            return;
//        }
//
//        List<LectureProgress> lectureProgresses = progressMap.getOrDefault(enrollment.getId(), List.of());
//        Review review = enrollment.getReview();
//        Integer reviewRating = (review != null) ? review.getRating() : null;
//
//        dto.setLecturesFinishedCount(lectureProgresses.size());
//        dto.setRating(reviewRating);
//        dto.setIsFinished(enrollment.getEnrollmentStatus() == EnrollmentStatus.COMPLETED);
//    }
//
//    @AfterMapping
//    protected void enrichLearn(@MappingTarget CourseLearningResponse dto,
//                               Course course,
//                               @Context LanguageCode language,
//                               @Context Enrollment enrollment) {
//        Optional<CourseTranslation> translationOpt = getTranslation(course, language);
//        translationOpt.ifPresent(t -> {
//            dto.setTitleShort(t.getTitleShort());
//            dto.setTitle(t.getTitle());
//        });
//
//        Review review = enrollment.getReview();
//        Integer reviewRating = (review != null) ? review.getRating() : null;
//
//        dto.setRating(reviewRating);
//        dto.setIsFinished(enrollment.getEnrollmentStatus() == EnrollmentStatus.COMPLETED);
//
//        dto.setCourseContents(courseVersionService.getActiveByCourseId(course.getId()).getCourseModules().stream()
//                .map(cc -> courseModuleMapper.toDtoLearn(cc, language, enrollment.getUser().getId()))
//                .toList());
//    }
//
//    @AfterMapping
//    protected void enrichFull(@MappingTarget CourseDtoFull dto,
//                              Course course,
//                              @Context LanguageCode language) {
//
//        Optional<CourseTranslation> translationOpt = getTranslation(course, language);
//        translationOpt.ifPresent(t -> {
//            dto.setLanguage(language);
//            dto.setTitleShort(t.getTitleShort());
//            dto.setTitle(t.getTitle());
//            dto.setDescriptionShort(t.getDescriptionShort());
//            dto.setDescription(t.getDescription());
//            dto.setDescriptionLong(t.getDescriptionLong());
//            dto.setWhatWillBeLearned(t.getWhatWillBeLearned());
//        });
//
//        // Get ALL tags for this course at once (both skills and topics)
//        List<Tag> allTags = course.getTags();
//
//        // Encode skills
//        List<String> encodedSkills = allTags.stream()
//                .filter(tag -> tag.getType() == TagType.SKILL)
//                .map(tag -> {
//                    String value = tag.getTranslations().stream()
//                            .filter(t -> t.getLanguage() == language)
//                            .findFirst()
//                            .map(TagTranslate::getValue)
//                            .orElse("");
//                    return tag.getId() + "_" + value;
//                })
//                .toList();
//
//        // Encode topics
//        List<String> encodedTopics = allTags.stream()
//                .filter(tag -> tag.getType() == TagType.TOPIC)
//                .map(tag -> {
//                    String value = tag.getTranslations().stream()
//                            .filter(t -> t.getLanguage() == language)
//                            .findFirst()
//                            .map(TagTranslate::getValue)
//                            .orElse("");
//                    return tag.getId() + "_" + value;
//                })
//                .toList();
//
//        dto.setSkillsGained(encodedSkills);
//        dto.setTopicsCovered(encodedTopics);
//
//        dto.setCourseContents(courseVersionService.getActiveByCourseId(course.getId()).getCourseModules().stream()
//                .map(cc -> courseModuleMapper.toDtoFull(cc, language))
//                .toList());
//    }
//
//    private Optional<CourseTranslation> getTranslation(Course course, LanguageCode language) {
//        return course.getTranslations().stream()
//                .filter(t -> t.getLanguage().equals(language))
//                .findFirst();
//    }
//
//
//    protected <T extends CoursePreviewResponse> void enrichCourseDtoPreview(
//            T dto,
//            Course course,
//            LanguageCode language,
//            Map<Long, Double> avgRatingsMap,
//            Map<Long, CourseVersion> courseVersionMap,
//            Map<Long, CourseSkillsAndInterestsView> courseSkillsAndInterestsViewMap
//    ) {
//        Double rating = avgRatingsMap.getOrDefault(course.getId(), 0.0);
//        dto.setAverageRating(rating != null ? rating : 0.0);
//
//        CourseVersion courseVersion = courseVersionMap.get(course.getId());
//        int contentCount = 0;
//        int lectureCount = 0;
//        if (courseVersion != null && courseVersion.getCourseModules() != null) {
//            contentCount = courseVersion.getCourseModules().size();
//            lectureCount = courseVersion.getCourseModules().stream()
//                    .mapToInt(content -> content.getCourseLectures() != null
//                            ? content.getCourseLectures().size() : 0)
//                    .sum();
//        }
//
//        dto.setCourseContentCount(contentCount);
//        dto.setCourseLectureCount(lectureCount);
//
//        dto.setSkillsGained(courseSkillsAndInterestsViewMap.getOrDefault(course.getId(), new CourseSkillsAndInterestsView()).getSkills());
//        dto.setTopicsCovered(courseSkillsAndInterestsViewMap.getOrDefault(course.getId(), new CourseSkillsAndInterestsView()).getInterests());
//
//        List<Language> courseLanguages = course.getTranslations().stream().map(CourseTranslation::getLanguage).toList();
//        dto.setTranslatedLanguages(courseLanguages);
//
//        Optional<CourseTranslation> translationOpt = getTranslation(course, language);
//        translationOpt.ifPresent(t -> {
//            dto.setTitleShort(t.getTitleShort());
//            dto.setTitle(t.getTitle());
//            dto.setUrlSlug(slugService.slugify(t.getTitleShort()));
//        });
//    }
//}