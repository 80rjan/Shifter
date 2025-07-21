import {useGlobalContext} from "./context/GlobalContext.tsx";
import {useCourseStorage} from "./context/CourseStorage.ts";
import {
    fetchRecommendedCoursesApi
} from "./api/courseApi.ts";
import {useEffect} from "react";
import {fetchUserEnrollmentsApi} from "./api/enrollmentApi.ts";

function AppLoader() {
    const {accessToken, authChecked} = useGlobalContext();
    const {
        setEnrollments,
        recommendedCourses,
        setRecommendedCourses,
    } = useCourseStorage();

    useEffect(() => {
        if (!authChecked) return;

        const load = async () => {
            // Recommended Courses
            try {
                const recommendedStored = JSON.parse(sessionStorage.getItem("recommendedCourses") || "null");
                if (recommendedStored) {
                    setRecommendedCourses(JSON.parse(recommendedStored));
                } else {
                    const recommended = await fetchRecommendedCoursesApi(accessToken || "");
                    setRecommendedCourses(recommended);
                }
            } catch (err) {
                console.error("Failed to fetch recommended courses:", err);
            }

            // Enrollments
            try {
                try {
                    if (accessToken) {
                        const enrollments = await fetchUserEnrollmentsApi(accessToken);
                        setEnrollments(enrollments);
                    }
                } catch (err) {
                    console.error("Failed to fetch enrollments:", err);
                }

            } catch (err) {
                console.error("Unexpected error in AppLoader:", err);
            }
        };


        load();
    }, [authChecked, accessToken]);

    useEffect(() => {
        sessionStorage.setItem("recommendedCourses", JSON.stringify(recommendedCourses));
    }, [recommendedCourses]);


    return null
}

export default AppLoader;