//package com.shifterwebapp.shifter.course.services;
//
//import com.shifterwebapp.shifter.Validate;
//import com.shifterwebapp.shifter.identity.domain.Expert;
//import com.shifterwebapp.shifter.identity.infrastructure.ExpertRepository;
//import com.shifterwebapp.shifter.catalog.infrastructure.mapper.CourseMapper;
//import com.shifterwebapp.shifter.catalog.web.response.CourseTranslateReq;
//import com.shifterwebapp.shifter.catalog.application.TagService;
//import com.shifterwebapp.shifter.catalog.domain.Course;
//import com.shifterwebapp.shifter.catalog.domain.CourseTranslation;
//import com.shifterwebapp.shifter.catalog.web.response.CourseDtoFull;
//import com.shifterwebapp.shifter.catalog.infrastructure.CourseRepository;
//import com.shifterwebapp.shifter.catalog.domain.CourseVersion;
//import com.shifterwebapp.shifter.catalog.infrastructure.CourseVersionRepository;
//import com.shifterwebapp.shifter.catalog.domain.CourseModule;
//import com.shifterwebapp.shifter.catalog.domain.CourseModuleTranslate;
//import com.shifterwebapp.shifter.catalog.web.response.CourseModuleTranslateReq;
//import com.shifterwebapp.shifter.catalog.domain.CourseLecture;
//import com.shifterwebapp.shifter.catalog.domain.CourseLectureTranslate;
//import com.shifterwebapp.shifter.catalog.web.response.CourseLectureDtoFull;
//import com.shifterwebapp.shifter.catalog.web.response.CourseLectureTranslateReq;
//import com.shifterwebapp.shifter.infrastructure.storage.MetaInfo;
//import com.shifterwebapp.shifter.infrastructure.storage.S3UploadResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class AdminCourseService implements ImplAdminCourseService{
//
//    private final CourseRepository courseRepository;
//    private final CourseVersionRepository courseVersionRepository;
//    private final Validate validate;
//    private final CourseModuleService courseModuleService;
//    private final TagService tagService;
//    private final CourseLectureService courseLectureService;
//    private final CourseMapper courseMapper;
//    private final ExpertRepository expertRepository;
//
//    @Transactional(readOnly = true)
//    @Override
//    public CourseDtoFull getFullCourse(Long courseId, LanguageCode language) {
//        validate.validateCourseExists(courseId);
//
//        Course course = courseRepository.findById(courseId).orElseThrow();
//
//        return courseMapper.toDtoFull(course, language);
//    }
//
//
//    @Transactional
//    @Override
//    public CourseVersion createCourse(CourseDtoFull courseDtoFull, Long expertId) {
//        LanguageCode language = courseDtoFull.getLanguage();
//
//        Expert expert = expertRepository.findById(expertId)
//                .orElseThrow(() -> new RuntimeException("Expert not found"));
//
//        Course course = buildCourse(List.of(expert), courseDtoFull, language);
//        course = courseRepository.save(course);
//
//        CourseVersion oldCourseVersion = courseVersionRepository
//                .findByActiveTrueAndCourse_Id(course.getId());
//
//        int newVersionNumber = 1;
//        if (oldCourseVersion != null) {
//            newVersionNumber = oldCourseVersion.getVersionNumber() + 1;
//            oldCourseVersion.setActive(false);
//            courseVersionRepository.save(oldCourseVersion);
//        }
//
//        CourseVersion courseVersion = CourseVersion.builder()
//                .versionNumber(newVersionNumber)
//                .active(true)
//                .creationDate(java.time.LocalDate.now())
//                .course(course)
//                .build();
//
//        List<CourseModule> contentList = courseDtoFull.getCourseContents().stream()
//                .map(content -> courseModuleService.buildCourseContent(content, courseVersion, language))
//                .toList();
//
//        courseVersion.setCourseModules(contentList);
//
//        List<Tag> skillTags =  tagService.processRawTags(
//                courseDtoFull.getSkillsGained(), TagType.SKILL, language
//        );
//        List<Tag> topicTags = tagService.processRawTags(
//                courseDtoFull.getTopicsCovered(), TagType.TOPIC, language
//        );
//
//        List<Tag> tagList = new ArrayList<>(skillTags);
//        tagList.addAll(topicTags);
//
//        course.setTags(tagList);
//
//        return courseVersionRepository.save(courseVersion);
//    }
//
//    private Course buildCourse(List<Expert> experts, CourseDtoFull courseDtoFull, LanguageCode language) {
//
//        int durationMinutes = courseDtoFull.getCourseContents().stream()
//                .flatMap(content -> content.getCourseLectures().stream())
//                .mapToInt(CourseLectureDtoFull::getDurationMinutes)
//                .sum();
//
//        Course course = Course.builder()
//                .imageUrl(courseDtoFull.getImageUrl())
//                .color(courseDtoFull.getColor())
//                .difficulty(courseDtoFull.getDifficulty())
//                .durationMinutes(durationMinutes)
//                .price(courseDtoFull.getPrice())
//                .experts(experts)
//                .build();
//
//        CourseTranslation courseTranslate = CourseTranslation.builder()
//                .language(language)
//                .titleShort(courseDtoFull.getTitleShort())
//                .title(courseDtoFull.getTitle())
//                .descriptionShort(courseDtoFull.getDescriptionShort())
//                .description(courseDtoFull.getDescription())
//                .descriptionLong(courseDtoFull.getDescriptionLong())
//                .whatWillBeLearned(courseDtoFull.getWhatWillBeLearned())
//                .course(course)
//                .build();
//
//        course.setTranslations(List.of(courseTranslate));
//        return course;
//    }
//
//
//    @Transactional
//    @Override
//    public Course translateCourse(CourseTranslateReq courseTranslateReq) {
//        validate.validateCourseExists(courseTranslateReq.getId());
//        validate.validateCourseTranslation(courseTranslateReq.getId(), courseTranslateReq.getLanguage());
//
//        CourseVersion courseVersion = courseVersionRepository.findByActiveTrueAndCourse_Id(courseTranslateReq.getId());
//        Course course = courseRepository.findById(courseTranslateReq.getId()).orElseThrow();
//        addCourseTranslation(course, courseTranslateReq);
//        translateContents(courseVersion, courseTranslateReq.getCourseContents(), courseTranslateReq.getLanguage());
//
//        tagService.processExistingTags(
//                courseTranslateReq.getSkillsGained(), TagType.SKILL, courseTranslateReq.getLanguage()
//        );
//        tagService.processExistingTags(
//                courseTranslateReq.getTopicsCovered(), TagType.TOPIC, courseTranslateReq.getLanguage()
//        );
//
//        return courseRepository.save(course);
//    }
//
//    private void addCourseTranslation(Course course, CourseTranslateReq dto) {
//        CourseTranslation translation = CourseTranslation.builder()
//                .language(dto.getLanguage())
//                .titleShort(dto.getTitleShort())
//                .title(dto.getTitle())
//                .descriptionShort(dto.getDescriptionShort())
//                .description(dto.getDescription())
//                .descriptionLong(dto.getDescriptionLong())
//                .whatWillBeLearned(dto.getWhatWillBeLearned())
//                .course(course)
//                .build();
//
//        course.getTranslations().add(translation);
//    }
//
//    private void translateContents(CourseVersion courseVersion, List<CourseModuleTranslateReq> contentDtos, LanguageCode language) {
//        for (CourseModule content : courseVersion.getCourseModules()) {
//            CourseModuleTranslateReq contentDto = contentDtos.stream()
//                    .filter(ct -> ct.getId().equals(content.getId()))
//                    .findFirst()
//                    .orElseThrow(() -> new RuntimeException(
//                            "Missing translation DTO for CourseModule id=" + content.getId()
//                    ));
//
//            addContentTranslation(content, contentDto, language);
//        }
//    }
//
//    private void addContentTranslation(CourseModule content, CourseModuleTranslateReq contentDto, LanguageCode language) {
//        CourseModuleTranslate contentTranslate = CourseModuleTranslate.builder()
//                .title(contentDto.getTitle())
//                .language(language)
//                .courseModule(content)
//                .build();
//
//        content.getTranslations().add(contentTranslate);
//        translateLectures(content, contentDto.getCourseLectures(), language);
//    }
//
//    private void translateLectures(CourseModule content, List<CourseLectureTranslateReq> lectureDtos, LanguageCode language) {
//        for (CourseLecture lecture : content.getCourseLectures()) {
//            CourseLectureTranslateReq lectureDto = lectureDtos.stream()
//                    .filter(ld -> ld.getId().equals(lecture.getId()))
//                    .findFirst()
//                    .orElseThrow(() -> new RuntimeException(
//                            "Missing translation DTO for CourseLecture id=" + lecture.getId()
//                    ));
//
//            CourseLectureTranslate lectureTranslate = CourseLectureTranslate.builder()
//                    .language(language)
//                    .title(lectureDto.getTitle())
//                    .description(lectureDto.getDescription())
//                    .contentText(lectureDto.getContentText())
//                    .courseLecture(lecture)
//                    .build();
//
//            lecture.getTranslations().add(lectureTranslate);
//        }
//    }
//
//
//    @Transactional
//    @Override
//    public void deleteCourseById(Long courseId) {
//        courseRepository.deleteById(courseId);
//    }
//
//    @Transactional
//    @Override
//    public Course updateCourseWithImagesAndFiles(Long courseId, List<S3UploadResponse> s3UploadResponses, Language language) {
//        validate.validateCourseExists(courseId);
//        Course course = courseRepository.findById(courseId).orElseThrow();
//        CourseVersion courseVersion = courseVersionRepository.findByActiveTrueAndCourse_Id(courseId);
//
//        for (S3UploadResponse s3UploadResponse : s3UploadResponses) {
//            if ("COURSE_IMAGE".equals(s3UploadResponse.getType())) {
//                course.setImageUrl(s3UploadResponse.getFileName());
//                continue;
//            }
//
//            MetaInfo meta = s3UploadResponse.getMeta();
//            Integer contentPosition = meta.getContentPosition();
//            Integer lecturePosition = meta.getLecturePosition();
//
//            if (contentPosition != null && lecturePosition != null) {
//                // Make the indexes 0 indexed
//                int contentIndex = contentPosition - 1;
//                int lectureIndex = lecturePosition - 1;
//
//                if (contentIndex >= 0 && contentIndex < courseVersion.getCourseModules().size()) {
//                    var courseContent = courseVersion.getCourseModules().get(contentIndex);
//                    if (lectureIndex >= 0 && lectureIndex < courseContent.getCourseLectures().size()) {
//                        CourseLecture courseLecture = courseContent.getCourseLectures().get(lectureIndex);
//                        CourseLectureTranslate lectureTranslate = courseLectureService.getByCourseLectureIdAndLanguage(
//                                courseLecture.getId(),
//                                language
//                        );
//                        lectureTranslate.setContentFileName(s3UploadResponse.getFileName());
//                    } else {
//                        // handle invalid lecture index
//                        System.err.println("Invalid lecture index: " + lectureIndex);
//                    }
//                } else {
//                    // handle invalid content index
//                    System.err.println("Invalid content index: " + contentIndex);
//                }
//            }
//        }
//        return courseRepository.save(course);
//    }
//
//    @Transactional
//    @Override
//    public Boolean lectureFileExistsInCourse(Long courseId, String fileName) {
//        return courseRepository.lectureFileExistsInCourse(courseId, fileName);
//    }
//}
