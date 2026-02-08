import axios from "axios";
import type {CourseFull} from "../types/CourseFull.tsx";


const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const createCourseApi = async (course: CourseFull, accessToken?: string): Promise<number> => {
    const courseIdResponse = await axios.post(
        `${backendUrl}/api/admin/courses/create`,
        course,
        {
            headers: {
                Authorization: `Bearer ${accessToken}`,
                "Content-Type": "application/json",
            }
        }
    );

    return courseIdResponse.data;
};

export const uploadCourseFilesApi = async (courseId: number, formData: FormData, accessToken?: string): Promise<number> => {
    const response = await axios.post(
        `${backendUrl}/api/admin/courses/${courseId}/upload`,
        formData,
        {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        }
    );

    return response.data;
};