import axios from "axios";

const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const createCourseApi = async (courseJsonStr: string, accessToken?: string): Promise<number> => {
    const courseIdResponse = await axios.post(
        `${backendUrl}/api/admin/courses/create`,
        courseJsonStr,
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