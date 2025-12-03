import {useEffect, useState} from "react";
import type {CoursePreview} from "../models/javaObjects/CoursePreview.tsx";
import {fetchCoursesApi, fetchEnrolledCoursesApi} from "../api/courseApi.ts";
import {useAuthContext} from "../context/AuthContext.tsx";
import {useCourseStorage} from "../context/CourseStorage.ts";
import type {CoursePreviewEnrolled} from "../models/javaObjects/CoursePreviewEnrolled.tsx";
import {useTranslation} from "react-i18next";
import type {Language} from "../models/types/Language.tsx";

export function useEnrolledCourses() {
    const {allCourses: allCoursesStorage, setAllCourses: setAllCoursesStorage} = useCourseStorage();
    const {accessToken} = useAuthContext();
    const [enrolledCourses, setEnrolledCourses] = useState<CoursePreviewEnrolled[]>([]);
    const [allCourses, setAllCourses] = useState<CoursePreview[]>(allCoursesStorage || []);
    const [loading, setLoading] = useState<boolean>(true);
    const { i18n } = useTranslation();

    useEffect(() => {
        setLoading(true);
        // Enrolled courses
        fetchEnrolledCoursesApi(accessToken || "", i18n.language as Language)
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

        fetchCoursesApi(accessToken || "", i18n.language as Language)
            .then(courses => {
                setAllCoursesStorage(courses);
                setAllCourses(courses);
                sessionStorage.setItem("allCourses", JSON.stringify(courses));
            })
            .catch(err => {
                console.error("Failed to fetch courses:", err);
            })
    }, [accessToken, i18n.language]);

    return {
        enrolledCourses,
        setEnrolledCourses,
        allCourses,
        loading
    }
}