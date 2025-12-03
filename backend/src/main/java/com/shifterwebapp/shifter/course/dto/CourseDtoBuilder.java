package com.shifterwebapp.shifter.course.dto;

import com.shifterwebapp.shifter.attribute.repository.AttributeRepository;
import com.shifterwebapp.shifter.attribute.service.AttributeService;
import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.course.CourseTranslate;
import com.shifterwebapp.shifter.course.mapper.CourseMapper;
import com.shifterwebapp.shifter.course.repository.CourseTranslateRepository;
import com.shifterwebapp.shifter.coursecontent.dto.CourseContentDtoLearn;
import com.shifterwebapp.shifter.coursecontent.mapper.CourseContentMapper;
import com.shifterwebapp.shifter.courselecture.dto.CourseLectureDtoLearn;
import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.enrollment.service.EnrollmentService;
import com.shifterwebapp.shifter.enums.AttributeType;
import com.shifterwebapp.shifter.enums.EnrollmentStatus;
import com.shifterwebapp.shifter.enums.Language;
import com.shifterwebapp.shifter.review.ReviewDto;
import com.shifterwebapp.shifter.review.service.ReviewService;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgress;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgressMapper;
import com.shifterwebapp.shifter.usercourseprogress.service.UserCourseProgressService;
import com.shifterwebapp.shifter.utils.SlugService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseDtoBuilder {

    private final ReviewService reviewService;
    private final EnrollmentService enrollmentService;
    private final UserCourseProgressService userCourseProgressService;
    private final CourseMapper courseMapper;
    private final CourseContentMapper courseContentMapper;
    private final UserCourseProgressMapper userCourseProgressMapper;
    private final CourseTranslateRepository courseTranslateRepository;
    private final AttributeService attributeService;
    private final AttributeRepository attributeRepository;
    private final SlugService slugService;

    private <T extends CourseDtoPreview> T enrichCourseDto(Course course, T dto, Language language) {
        Double rating = reviewService.getAverageRatingByCourse(course.getId());
        int contentCount = course.getCourseContents() != null ? course.getCourseContents().size() : 0;
        int lectureCount = course.getCourseContents() != null
                ? course.getCourseContents().stream()
                .mapToInt(content -> content.getCourseLectures() != null
                        ? content.getCourseLectures().size() : 0)
                .sum()
                : 0;

        dto.setAverageRating(rating != null ? rating : 0.0);
        dto.setCourseContentCount(contentCount);
        dto.setCourseLectureCount(lectureCount);

        List<String> skillsGained = attributeService.getSkillsByCourseId(course.getId(), language);
        List<String> topicsCovered = attributeService.getTopicsByCourseId(course.getId(), language);
        dto.setSkillsGained(skillsGained);
        dto.setTopicsCovered(topicsCovered);

        String enCourseTitle = slugService.getEnCourseTitle(course.getId());
        String slug = slugService.slugify(enCourseTitle);
        dto.setUrlSlug(slug);

        List<Language> courseLanguages = courseTranslateRepository.getCourseLanguages(course.getId());
        dto.setTranslatedLanguages(courseLanguages);

        return dto;
    }


    public CourseDtoPreview getCourseDtoPreview(Course course, Language language) {
        CourseDtoPreview courseDtoPreview = courseMapper.toDtoPreview(course, language);

        return enrichCourseDto(course, courseDtoPreview, language);
    }

    public List<CourseDtoPreview> getCourseDtoPreview(List<Course> courses, Language language) {
        List<CourseDtoPreview> courseDtoPreviewList = new ArrayList<>();
        for (Course course : courses) {
            CourseDtoPreview dto = getCourseDtoPreview(course, language);

            courseDtoPreviewList.add(dto);
        }

        return courseDtoPreviewList;
    }

    public CourseDtoDetail getCourseDtoDetail(Course course, Language language) {
        CourseDtoDetail courseDtoDetail = courseMapper.toDtoDetail(course, language);

        return enrichCourseDto(course, courseDtoDetail, language);
    }

    public List<CourseDtoDetail> getCourseDtoDetail(List<Course> courses, Language language) {
        List<CourseDtoDetail> courseDtoDetailList = new ArrayList<>();
        for (Course course : courses) {
            CourseDtoDetail dto = getCourseDtoDetail(course, language);

            courseDtoDetailList.add(dto);
        }

        return courseDtoDetailList;
    }

    public CourseDtoPreviewEnrolled getCourseDtoPreviewEnrolled(Course course, Long userId, Language language) {
        CourseDtoPreviewEnrolled courseDtoEnrolled = courseMapper.toDtoPreviewEnrolled(course, language);
        Enrollment enrollment = enrollmentService.getEnrollmentByUserAndCourse(userId, courseDtoEnrolled.getId());
        List<UserCourseProgress> userCourseProgress = userCourseProgressService.getUserCourseProgressByEnrollmentAndCompletedTrue(enrollment.getId());
        Integer reviewRating = null;
        if (reviewService.hasBeenReviewedByUser(userId, course.getId()))
            reviewRating = reviewService.getReviewByUserAndCourse(userId, course.getId()).getRating();

        courseDtoEnrolled.setLecturesFinishedCount(userCourseProgress.size());
        courseDtoEnrolled.setRating(reviewRating);
        courseDtoEnrolled.setIsFinished(enrollment.getEnrollmentStatus() == EnrollmentStatus.COMPLETED);

        return enrichCourseDto(course, courseDtoEnrolled, language);
    }

    public List<CourseDtoPreviewEnrolled> getCourseDtoPreviewEnrolled(List<Course> courses, Long userId, Language language) {
        List<CourseDtoPreviewEnrolled> courseDtoPreviewEnrolledList = new ArrayList<>();
        for (Course course : courses) {
            CourseDtoPreviewEnrolled dto = getCourseDtoPreviewEnrolled(course, userId, language);

            courseDtoPreviewEnrolledList.add(dto);
        }

        return courseDtoPreviewEnrolledList;
    }

    public CourseDtoLearn getCourseDtoLearn(Course course, Enrollment enrollment, Language language) {
        List<UserCourseProgress> userCourseProgress = userCourseProgressService.getUserCourseProgressByEnrollment(enrollment.getId());

        Integer reviewRating = null;
        ReviewDto review = reviewService.getReviewByEnrollment(enrollment.getId());
        if (review != null)
            reviewRating = review.getRating();

        Map<Long, UserCourseProgress> progressMap = userCourseProgress.stream()
                .collect(Collectors.toMap(
                        UserCourseProgress::getCourseLectureId,
                        Function.identity()
                ));

        CourseDtoLearn courseDto = courseMapper.toDtoLearn(course, language);

        courseDto.setRating(reviewRating);
        courseDto.setIsFinished(enrollment.getEnrollmentStatus() == EnrollmentStatus.COMPLETED);
        for (CourseContentDtoLearn contentDto : courseDto.getCourseContents()) {
            for (CourseLectureDtoLearn lectureDto : contentDto.getCourseLectures()) {
                UserCourseProgress progress = progressMap.get(lectureDto.getId());
                lectureDto.setUserCourseProgress(userCourseProgressMapper.toDto(progress));
            }
        }

        return courseDto;
    }

    public List<CourseDtoLearn> getCourseDtoLearn(List<Course> courses, Enrollment enrollment, Language language) {
        List<CourseDtoLearn> courseDtoLearnList = new ArrayList<>();
        for (Course course : courses) {
            CourseDtoLearn dto = getCourseDtoLearn(course, enrollment, language);

            courseDtoLearnList.add(dto);
        }

        return courseDtoLearnList;
    }

    public CourseDtoFull getCourseDtoFull(Course course) {
        CourseDtoFull dto = courseMapper.toDtoFull(course, Language.EN);

        if (dto.getCourseContents() == null || dto.getCourseContents().isEmpty()) {
            dto.setCourseContents(
                    courseContentMapper.toDtoFull(course.getCourseContents(), Language.EN)
            );
        }

        // COURSE
        CourseTranslate translate = courseTranslateRepository.getCourseTranslateByCourseIdAndLanguage(course.getId(), Language.EN);
        dto.setLanguage(Language.EN);
        dto.setTitleShort(translate.getTitleShort());
        dto.setTitle(translate.getTitle());
        dto.setDescriptionShort(translate.getDescriptionShort());
        dto.setDescription(translate.getDescription());
        dto.setDescriptionLong(translate.getDescriptionLong());
        dto.setWhatWillBeLearned(translate.getWhatWillBeLearned());


        List<String> skillsGained = attributeRepository.findByCourseIdAndTypeAndLanguage(
                course.getId(),
                AttributeType.SKILL,
                Language.EN
        ).stream().map(a -> a.getId() + "_" + a.getTranslations().get(0).getValue()).toList();
        List<String> topicsCovered = attributeRepository.findByCourseIdAndTypeAndLanguage(
                course.getId(),
                AttributeType.TOPIC,
                Language.EN
        ).stream().map(a -> a.getId() + "_" + a.getTranslations().get(0).getValue()).toList();
        dto.setSkillsGained(skillsGained);
        dto.setTopicsCovered(topicsCovered);


        // COURSE CONTENT

        return dto;
    }

    public List<CourseDtoFull> getCourseDtoFull(List<Course> courses) {
        List<CourseDtoFull> courseDtoFullList = new ArrayList<>();
        for (Course course : courses) {
            CourseDtoFull dto = getCourseDtoFull(course);

            courseDtoFullList.add(dto);
        }

        return courseDtoFullList;
    }
}
