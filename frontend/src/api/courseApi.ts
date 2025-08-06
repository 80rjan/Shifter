import axios from "axios";
import type {CoursePreview} from "../types/CoursePreview.tsx";
import type {CourseDetail} from "../types/CourseDetail.tsx";

const backendUrl = import.meta.env.VITE_BACKEND_URL;

// export const fetchCoursesApi = async (accessToken?: string, params?: FilterParams, signal?: AbortSignal): Promise<CoursePreview[]> => {
//     const res = await axios.get(
//         `${backendUrl}/api/courses`,
//         {
//             params,
//             paramsSerializer: params => qs.stringify(params, {arrayFormat: 'repeat'}),
//             signal,
//             headers: accessToken ? { Authorization: `Bearer ${accessToken}` } : undefined
//
//         }
//     )
//
//     return res.data;
// }
export const fetchCoursesApi = async (accessToken?: string, signal?: AbortSignal): Promise<CoursePreview[]> => {
    const res = await axios.get(
        `${backendUrl}/api/courses`,
        {
            signal,
            headers: accessToken ? { Authorization: `Bearer ${accessToken}` } : undefined

        }
    )

    return res.data;
}

export const fetchRecommendedCoursesApi = async (accessToken: string): Promise<CoursePreview[]> => {
    const res = await axios.get(`${backendUrl}/api/courses/recommended`, {
        headers: {
            Authorization: `Bearer ${accessToken}`
        }
    });

    return res.data;
}

export const fetchEnrolledCoursesApi = async (accessToken: string): Promise<CoursePreview[]> => {
    const res = await axios.get(`${backendUrl}/api/courses/enrolled`, {
        headers: {
            Authorization: `Bearer ${accessToken}`
        }
    });

    return res.data;
}

export const fetchCourseDetailsApi = async (courseId: number, signal?: AbortSignal): Promise<CourseDetail> => {
    const res = await axios.get(
        `${backendUrl}/api/courses/${courseId}`,
        { signal }
    );

    return res.data;
}

export const fetchCoursesTopicsApi = async (): Promise<string[]> => {
    const res = await axios.get(`${backendUrl}/api/courses/topics`);
    return res.data;
}

export const fetchCoursesSkillsApi = async (): Promise<string[]> => {
    const res = await axios.get(`${backendUrl}/api/courses/skills`);
    return res.data;
}


