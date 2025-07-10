import {useParams} from "react-router-dom";
import { fetchCourseDetailsApi } from "../api/courses.ts";
import {useEffect, useState} from "react";
import HeroCourseDetails from "../components/HeroCourseDetails.tsx";
import CourseDetailsInfo from "../components/CourseDetailsInfo.tsx";
import CoursesCarouselCourseDetails from "../components/CoursesCarouselCourseDetails.tsx";
import type {CourseDetail} from "../types/CourseDetail.tsx";

function CourseDetails() {
    const [loading, setLoading] = useState<boolean>(true);
    const { courseId } = useParams<{ courseId: string; courseTitle: string }>();
    const [course, setCourse] = useState<CourseDetail | null>(null);

    useEffect(() => {
        setLoading(true);

        fetchCourseDetailsApi(Number(courseId))
            .then(data => {
                setCourse(data);
                console.log(data)
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
                        <HeroCourseDetails course={course}/>
                        <CourseDetailsInfo course={course}/>
                        <CoursesCarouselCourseDetails/>
                    </>
            }
        </main>
    )
}

export default CourseDetails;