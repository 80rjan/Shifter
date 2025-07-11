import {useParams} from "react-router-dom";
import { fetchCourseDetailsApi } from "../api/courseApi.ts";
import {useEffect, useState} from "react";
import HeroCourseDetails from "../components/HeroCourseDetails.tsx";
import CourseDetailsInfo from "../components/CourseDetailsInfo.tsx";
import CoursesCarouselCourseDetails from "../components/CoursesCarouselCourseDetails.tsx";
import type {CourseDetail} from "../types/CourseDetail.tsx";
import {enrollUserApi} from "../api/enrollmentApi.ts";
import {useGlobalContext} from "../context/GlobalContext.tsx";
import {showInfoToast} from "../utils/showInfoToast.ts";

function CourseDetails() {
    const { user, accessToken } = useGlobalContext();
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
                loading ?
                    <div className="flex flex-col gap-12 justify-center items-center h-screen">
                        <div className="w-20 loader"></div>
                        <span className="text-xl font-semibold text-black/40">Loading...</span>
                    </div> :
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