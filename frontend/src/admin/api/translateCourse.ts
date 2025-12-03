import axios from "axios";
import type {CourseFull} from "../types/CourseFull.tsx";
import type {CourseTranslate} from "../types/CourseTranslate.tsx";

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const fetchCourseFullApi = async (courseId: number, accessToken: string): Promise<CourseFull> => {
    const res = await axios.get(
        `${backendUrl}/api/admin/courses/${courseId}`,
        {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        }
    )

    return res.data;
}

export const translateCourseApi = async (course: CourseTranslate, accessToken: string) => {
    const res = await axios.post(
        `${backendUrl}/api/admin/courses/translate`,
        course,
        {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        }
    )

    return res.data;
}