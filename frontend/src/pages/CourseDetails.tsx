import {useParams} from "react-router-dom";
import { fetchCourseDetailsApi } from "../api/courseApi.ts";
import {useEffect, useState} from "react";
import HeroCourseDetails from "../components/HeroCourseDetails.tsx";
import CourseDetailsInfo from "../components/CourseDetailsInfo.tsx";
import CoursesCarouselCourseDetails from "../components/CoursesCarouselCourseDetails.tsx";
import type {CourseDetail} from "../models/javaObjects/course/CourseDetail.tsx";
import HeroCourseDetailsSkeleton from "../components/skeletons/HeroCourseDetailsSkeleton.tsx";
import CourseDetailsInfoSkeleton from "../components/skeletons/CourseDetailsInfoSkeleton.tsx";
import {useEnrollUserInCourse} from "../hooks/useEnrollUserInCourse.tsx";
import {useTranslation} from "react-i18next";
import type {Language} from "../models/types/Language.tsx";
import CoursesCarouselCourseDetailsSkeleton from "../components/skeletons/CoursesCarouselCourseDetailsSkeleton.tsx";

function CourseDetails() {
    const [loading, setLoading] = useState<boolean>(true);
    const { courseId } = useParams<{ courseId: string; courseTitle: string }>();
    const [course, setCourse] = useState<CourseDetail>({
        translatedLanguages: [],
        urlSlug: "",
        averageRating: 0,
        color: "",
        courseContentCount: 0,
        courseContents: [],
        courseLectureCount: 0,
        description: "",
        descriptionLong: "",
        descriptionShort: "",
        difficulty: "BEGINNER",
        durationMinutes: 0,
        id: 0,
        imageUrl: "",
        price: 0,
        skillsGained: [],
        title: "",
        titleShort: "",
        topicsCovered: [],
        whatWillBeLearned: []
    });
    const { i18n } = useTranslation();
    const { enroll } = useEnrollUserInCourse(Number(courseId), course?.urlSlug || "");

    useEffect(() => {
        setLoading(true);

        fetchCourseDetailsApi(Number(courseId), i18n.language as Language)
            .then(data => {
                setCourse(data);
            })
            .catch(err => {
                console.error("Error fetching course details: ", err);
            })
            .finally(() => {
                setLoading(false);
            })

    }, [i18n.language, courseId])

    return (
        <main className="bg-main text-black-text">
            {
                loading ?
                    <>
                        <HeroCourseDetailsSkeleton />
                        <CourseDetailsInfoSkeleton />
                        <CoursesCarouselCourseDetailsSkeleton />
                    </> :
                    <>
                        <HeroCourseDetails course={course} enrollUser={enroll}/>
                        <CourseDetailsInfo course={course}/>
                        <CoursesCarouselCourseDetails courseId={course.id}/>
                    </>
            }
        </main>
    )
}

export default CourseDetails;