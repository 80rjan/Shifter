import {useGlobalContext} from "./context/GlobalContext.tsx";
import {useCourseStorage} from "./context/CourseStorage.ts";
import {
    fetchCoursesApi,
    fetchCoursesSkillsApi,
    fetchCoursesTopicsApi,
    fetchRecommendedCoursesApi
} from "./api/courseApi.ts";
import {useEffect} from "react";
import {fetchUserEnrollmentsApi} from "./api/enrollmentApi.ts";

function AppLoader() {
    const {accessToken} = useGlobalContext();
    const {
        setEnrollments,
        setAllCourses,
        setRecommendedCourses,
        setTopics,
        setSkills
    } = useCourseStorage();

    useEffect(() => {
        const load = async () => {
            try {
                // Topics
                try {
                    const topicsStored = sessionStorage.getItem("courseTopics");
                    if (topicsStored) {
                        setTopics(JSON.parse(topicsStored));
                    } else {
                        const topics = await fetchCoursesTopicsApi();
                        setTopics(topics);
                        sessionStorage.setItem("courseTopics", JSON.stringify(topics));
                    }
                } catch (err) {
                    console.error("Failed to fetch topics:", err);
                }

                // Skills
                try {
                    const skillsStored = sessionStorage.getItem("courseSkills");
                    if (skillsStored) {
                        setSkills(JSON.parse(skillsStored));
                    } else {
                        const skills = await fetchCoursesSkillsApi();
                        setSkills(skills);
                        sessionStorage.setItem("courseSkills", JSON.stringify(skills));
                    }
                } catch (err) {
                    console.error("Failed to fetch skills:", err);
                }

                // Courses
                try {
                    const allCoursesStored = sessionStorage.getItem("allCourses");
                    if (allCoursesStored) {
                        setAllCourses(JSON.parse(allCoursesStored));
                    } else {
                        const courses = await fetchCoursesApi(accessToken || "");
                        setAllCourses(courses);
                        sessionStorage.setItem("allCourses", JSON.stringify(courses));
                    }
                } catch (err) {
                    console.error("Failed to fetch courses:", err);
                }

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