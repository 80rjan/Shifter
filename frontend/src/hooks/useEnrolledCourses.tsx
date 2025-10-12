import {useEffect, useState} from "react";
import type {CoursePreview} from "../models/javaObjects/CoursePreview.tsx";
import {fetchCoursesApi, fetchEnrolledCoursesApi} from "../api/courseApi.ts";
import {useAuthContext} from "../context/AuthContext.tsx";
import {useCourseStorage} from "../context/CourseStorage.ts";
import type {CoursePreviewEnrolled} from "../models/javaObjects/CoursePreviewEnrolled.tsx";

export function useEnrolledCourses() {
    const {allCourses: allCoursesStorage, setAllCourses: setAllCoursesStorage} = useCourseStorage();
    const {accessToken} = useAuthContext();
    const [enrolledCourses, setEnrolledCourses] = useState<CoursePreviewEnrolled[]>([]);
    const [allCourses, setAllCourses] = useState<CoursePreview[]>(allCoursesStorage || []);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        setLoading(true);
        // Enrolled courses
        fetchEnrolledCoursesApi(accessToken || "")
            .then(data => {
                setEnrolledCourses(data);
            })
            .catch(error => {
                console.error("Failed to fetch enrolled courses:", error);
            })
            .finally(() => setLoading(false));

        // All courses to get the users favorite courses
        if (allCoursesStorage && allCoursesStorage.length > 0) {
            return;
        }
        const storedCourses = sessionStorage.getItem("allCourses");
        const parsedCourses = storedCourses ? JSON.parse(storedCourses) : null;
        if (parsedCourses) {
            setAllCoursesStorage(parsedCourses);
            setAllCourses(parsedCourses);
            return;
        }

        fetchCoursesApi(accessToken || "")
            .then(courses => {
                setAllCoursesStorage(courses);
                setAllCourses(courses);
                sessionStorage.setItem("allCourses", JSON.stringify(courses));
            })
            .catch(err => {
                console.error("Failed to fetch courses:", err);
            })
    }, [accessToken]);

    return {
        enrolledCourses,
        setEnrolledCourses,
        allCourses,
        loading
    }
}