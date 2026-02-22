import {useEffect, useState} from "react";
import type {CourseTranslate} from "../../models/expert/types/CourseTranslate.tsx";
import {useNavigate, useParams} from "react-router-dom";
import {useAuthContext} from "../../context/AuthContext.tsx";
import type {CourseFull} from "../../models/expert/types/CourseFull.tsx";
import {fetchCourseFullApi, translateCourseApi} from "../../api/expert/translateCourse.ts";
import type {ContentType} from "../../models/types/ContentType.tsx";
import CourseCard from "../../components/CourseCard.tsx";
import {uploadCourseFilesApi} from "../../api/expert/addCourse.ts";

export function useAdminTranslateCourse() {
    // TODO: make dynamic language and courseid work
    const [courseFull, setCourseFull] = useState<CourseFull>({
        id: -1,
        color: "",
        courseContents: [],
        description: "",
        descriptionLong: "",
        descriptionShort: "",
        difficulty: "BEGINNER",
        durationMinutes: 0,
        imageUrl: "",
        language: "EN",
        price: 0,
        skillsGained: [],
        title: "",
        titleShort: "",
        topicsCovered: [],
        whatWillBeLearned: []
    });
    const [courseTranslate, setCourseTranslate] = useState<CourseTranslate>({
        id: -1,
        language: "MK",
        titleShort: "",
        title: "",
        descriptionShort: "",
        description: "",
        descriptionLong: "",
        whatWillBeLearned: [],
        skillsGained: [],
        topicsCovered: [],
        courseContents: [],
    })
    const [courseLectureFiles, setCourseLectureFiles] = useState<{
        file: File,
        type: ContentType,
        contentPosition: number,
        lecturePosition: number
    }[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);
    const [isFetchingCourse,setIsFetchingCourse] = useState<boolean>(false);
    const {courseId} = useParams();
    const {accessToken} = useAuthContext();
    const navigate = useNavigate();

    useEffect(() => {
        if (!courseId) {
            console.warn("No courseId found in URL");
            navigate("/admin/courses");
            return;
        }

       setIsFetchingCourse(true);
        fetchCourseFullApi(+courseId, accessToken || "")
            .then(courseRes => {
                setCourseFull(courseRes);
                setCourseTranslate({
                    ...courseTranslate,
                    id: courseRes.id,
                    courseContents: courseRes.courseContents.map(content => ({
                        id: content.id,
                        title: "",
                        courseLectures: content.courseLectures.map(lecture => ({
                            id: lecture.id,
                            title: "",
                            description: "",
                            durationMinutes: 0,
                            contentText: "",
                            contentFileName: "",
                            contentType: lecture.contentType
                        }))
                    }))
                })
            })
            .catch(error => {
                console.error("Error fetching full course: ", error);
            })
            .finally(() =>setIsFetchingCourse(false));
    }, [])

    console.log(courseFull)
    console.log(courseTranslate)

    const courseCard = courseFull ? (
        <CourseCard card={{
            id: 0,
            imageUrl: courseFull.imageUrl,
            color: courseFull.color,
            titleShort: courseTranslate.titleShort,
            title: courseTranslate.title,
            difficulty: courseFull.difficulty,
            durationMinutes: courseFull.durationMinutes,
            price: courseFull.price,
            averageRating: 0,
            skillsGained: courseTranslate.skillsGained.map(skill => skill.value),
            topicsCovered: courseTranslate.topicsCovered.map(topic => topic.value),
            courseContentCount: courseFull.courseContents.length,
            courseLectureCount: courseFull.courseContents.flatMap(c => c.courseLectures).length,
            urlSlug: "",
            translatedLanguages: ["EN", "MK"]
        }}/>
    ) : null;

    const handleTranslateCourse = async () => {
        const countFileOrVideoLectures = courseTranslate.courseContents
            .flatMap(content => content.courseLectures)
            .filter(lecture => lecture.contentType === "FILE" || lecture.contentType === "VIDEO" || lecture.contentType === "QUIZ" || lecture.contentType === "TOOL")
            .length;

        if (!courseTranslate.titleShort || !courseTranslate.title ||
            !courseTranslate.descriptionShort || !courseTranslate.description || !courseTranslate.descriptionLong ||
            courseTranslate.whatWillBeLearned.length === 0 || courseTranslate.skillsGained.length === 0 || courseTranslate.topicsCovered.length === 0) {
            setError("Please fill in all course details");
            return;
        }
        if (
            courseTranslate.courseContents.length === 0 || courseTranslate.courseContents.some(content => content.title.length === 0) ||
            courseTranslate.courseContents.some(content => content.courseLectures.length === 0) ||
            courseTranslate.courseContents.some(content => content.courseLectures.some(lecture => !lecture.title || !lecture.description || !lecture.contentText)) ||
            courseLectureFiles.length < countFileOrVideoLectures
        ) {
            setError("Please add at least one content with lectures, fill in all fields and upload files for all file/video/quiz/tool lectures");
            return;
        }

        const formData = new FormData();
        courseLectureFiles.forEach(courseLectureFile => {
            formData.append("files", courseLectureFile.file);
            formData.append("types", courseLectureFile.type);
            formData.append("meta", JSON.stringify({
                contentPosition: courseLectureFile.contentPosition,
                lecturePosition: courseLectureFile.lecturePosition
            }));
        });

        setLoading(true);
        setError(null);

        try {
            const courseId = await translateCourseApi(courseTranslate, accessToken || "");
            if (!courseId) {
                throw new Error("Failed to translate course.");
            }

            try {
                await uploadCourseFilesApi(courseId, formData, accessToken || "");
                navigate('/')
            } catch (err) {
                console.error("Error uploading course lecture files:", err);
                setError("An error occurred while uploading lecture files. Please try again or contact support.");
            }
        } catch (err) {
            console.error("Error translating course:", err);
            setError("An error occurred while translating the course. Please try again or contact support.");
        } finally {
            setLoading(false);
        }
    };


    return {
        courseFull,
        setCourseFull,
        courseTranslate,
        setCourseTranslate,
        courseCard,
        courseLectureFiles,
        setCourseLectureFiles,
        error,
        loading,
        isFetchingCourse,
        handleTranslateCourse
    }
}