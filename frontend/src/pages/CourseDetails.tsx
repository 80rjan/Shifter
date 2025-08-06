import {useParams} from "react-router-dom";
import { fetchCourseDetailsApi } from "../api/courseApi.ts";
import {useEffect, useState} from "react";
import HeroCourseDetails from "../components/HeroCourseDetails.tsx";
import CourseDetailsInfo from "../components/CourseDetailsInfo.tsx";
import CoursesCarouselCourseDetails from "../components/CoursesCarouselCourseDetails.tsx";
import type {CourseDetail} from "../types/CourseDetail.tsx";
import {enrollUserApi} from "../api/enrollmentApi.ts";
import {useAuthContext} from "../context/AuthContext.tsx";
import {showInfoToast} from "../utils/showInfoToast.ts";
import {useCourseStorage} from "../context/CourseStorage.ts";
import HeroCourseDetailsSkeleton from "../components/skeletons/HeroCourseDetailsSkeleton.tsx";
import CourseDetailsInfoSkeleton from "../components/skeletons/CourseDetailsInfoSkeleton.tsx";

function CourseDetails() {
    const { user, accessToken, loading: authLoading } = useAuthContext();
    const { enrollments, setEnrollments } = useCourseStorage();
    const [loading, setLoading] = useState<boolean>(true);
    const { courseId } = useParams<{ courseId: string; courseTitle: string }>();
    const [course, setCourse] = useState<CourseDetail | null>(null);

    const handleBuyCourse = () => {
        if (!user) {
            showInfoToast("Please log in to enroll in the course.");
            return Promise.reject("No user logged in");
        }

        return enrollUserApi(Number(courseId), accessToken || "")
            .then(() => {
                if (enrollments) {
                    setEnrollments(enrollments.filter((enrollment) => enrollment.courseId !== Number(courseId)));
                }
                console.log("User enrolled in course successfully");
            })
            .catch((error) => {
                console.error("Error enrolling user in course:", error);
            })
    }

    useEffect(() => {
        setLoading(true);

        fetchCourseDetailsApi(Number(courseId))
            .then(data => {
                setCourse(data);
            })
            .catch(err => {
                console.error("Error fetching course details: ", err);
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
                        <HeroCourseDetails course={course} enrollUser={handleBuyCourse}/>
                        <CourseDetailsInfo course={course}/>
                        <CoursesCarouselCourseDetails/>
                    </>
            }
        </main>
    )
}

export default CourseDetails;