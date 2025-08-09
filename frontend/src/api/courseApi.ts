import axios from "axios";
import type {CoursePreview} from "../models/javaObjects/CoursePreview.tsx";
import type {CourseDetail} from "../models/javaObjects/CourseDetail.tsx";
import type {CourseFull} from "../models/javaObjects/CourseFull.tsx";

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
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
            headers: accessToken ? {Authorization: `Bearer ${accessToken}`} : undefined

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
        {signal}
    );

    return res.data;
}

export const fetchCourseFullApi = async (courseId: number, accessToken: string): Promise<CourseFull> => {
    const res = await axios.get(
        `${backendUrl}/api/courses/${courseId}/enrolled`, {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        });

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


