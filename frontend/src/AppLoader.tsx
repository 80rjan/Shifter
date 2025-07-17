import {useGlobalContext} from "./context/GlobalContext.tsx";
import {useCourseStorage} from "./context/CourseStorage.ts";
import {
    fetchRecommendedCoursesApi
} from "./api/courseApi.ts";
import {useEffect} from "react";
import {fetchUserEnrollmentsApi} from "./api/enrollmentApi.ts";

function AppLoader() {
    const {accessToken} = useGlobalContext();
    const {
        setEnrollments,
        setRecommendedCourses,
    } = useCourseStorage();

    useEffect(() => {
        const load = async () => {
            try {
                // Enrollments
                try {
                    if (accessToken) {
                        const enrollments = await fetchUserEnrollmentsApi(accessToken);
                        setEnrollments(enrollments);
                    }
                } catch (err) {
                    console.error("Failed to fetch enrollments:", err);
                }

                // Recommended Courses
                try {
                    const recommendedStored = sessionStorage.getItem("recommendedCourses");
                    if (recommendedStored) {
                        setRecommendedCourses(JSON.parse(recommendedStored));
                    } else {
                        const recommended = await fetchRecommendedCoursesApi(accessToken || "");
                        setRecommendedCourses(recommended);
                        sessionStorage.setItem("recommendedCourses", JSON.stringify(recommended));
                    }
                } catch (err) {
                    console.error("Failed to fetch recommended courses:", err);
                }

            } catch (err) {
                console.error("Unexpected error in AppLoader:", err);
            }
        };


        load();
    }, [accessToken]);


    return null
}

export default AppLoader;