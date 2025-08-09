import {useParams} from "react-router-dom";
import { fetchCourseDetailsApi } from "../api/courseApi.ts";
import {useEffect, useState} from "react";
import HeroCourseDetails from "../components/HeroCourseDetails.tsx";
import CourseDetailsInfo from "../components/CourseDetailsInfo.tsx";
import CoursesCarouselCourseDetails from "../components/CoursesCarouselCourseDetails.tsx";
import type {CourseDetail} from "../models/javaObjects/CourseDetail.tsx";
import {useAuthContext} from "../context/AuthContext.tsx";
import HeroCourseDetailsSkeleton from "../components/skeletons/HeroCourseDetailsSkeleton.tsx";
import CourseDetailsInfoSkeleton from "../components/skeletons/CourseDetailsInfoSkeleton.tsx";
import {useEnrollUserInCourse} from "../hooks/useEnrollUserInCourse.tsx";

function CourseDetails() {
    const { loading: authLoading } = useAuthContext();
    const [loading, setLoading] = useState<boolean>(true);
    const { courseId } = useParams<{ courseId: string; courseTitle: string }>();
    const [course, setCourse] = useState<CourseDetail>({
        color: "",
        courseContentCount: 0,
        courseContents: [],
        description: "",
        descriptionLong: "",
        descriptionShort: "",
        difficulty: "BEGINNER",
        durationMinutes: 0,
        id: 0,
        imageUrl: "",
        price: 0,
        rating: 0,
        ratingCount: 0,
        skillsGained: [],
        title: "",
        titleShort: "",
        topicsCovered: [],
        whatWillBeLearned: []
    });

    const { enroll } = useEnrollUserInCourse(Number(courseId), course?.titleShort);

    useEffect(() => {
        setLoading(true);

        fetchCourseDetailsApi(Number(courseId))
            .then(data => {
                setCourse(data);
            })
            .catch(err => {
                console.error("Error fetching javaObjects details: ", err);
            })
            .finally(() => {
                setLoading(false);
            })

    }, [courseId])

    return (
        <main className="bg-white">
            {
                (authLoading || loading) ?
                    <>
                        <HeroCourseDetailsSkeleton />
                        <CourseDetailsInfoSkeleton />
                        <CoursesCarouselCourseDetails />
                    </> :
                    <>
                        <HeroCourseDetails course={course} enrollUser={enroll}/>
                        <CourseDetailsInfo course={course}/>
                        <CoursesCarouselCourseDetails/>
                    </>
            }
        </main>
    )
}

export default CourseDetails;