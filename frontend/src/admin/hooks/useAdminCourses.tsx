import {useEffect, useState} from "react";
import type {CoursePreview} from "../../models/javaObjects/course/CoursePreview.tsx";
import {fetchCoursesApi} from "../../api/courseApi.ts";
import {useAuthContext} from "../../context/AuthContext.tsx";

export function useAdminCourses() {
    const { accessToken } = useAuthContext()
    const [courses, setCourses] = useState<CoursePreview[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchCourses = async () => {
            setLoading(true);

            fetchCoursesApi(accessToken || "", "EN")
                .then(data => {
                    setCourses(data)
                })
                .catch(error => {
                    console.error(error)
                })
                .finally(() => setLoading(false));
        }

        fetchCourses();
    }, [])

    return {
        courses,
        loading,
    }
}