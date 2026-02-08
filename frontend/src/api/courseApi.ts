import axios, {type AxiosResponse} from "axios";
import type {CoursePreview} from "../models/javaObjects/course/CoursePreview.tsx";
import type {CourseDetail} from "../models/javaObjects/course/CourseDetail.tsx";
import type {CourseLearn} from "../models/javaObjects/course/CourseLearn.tsx";
import type {CoursePreviewEnrolled} from "../models/javaObjects/course/CoursePreviewEnrolled.tsx";
import type {Language} from "../models/types/Language.tsx";


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
export const fetchCoursesApi = async (accessToken: string, language: Language, signal?: AbortSignal): Promise<CoursePreview[]> => {

    const res = await axios.get(
        `${backendUrl}/api/courses?language=${language}`,
        {
            signal,
            headers: accessToken ? {Authorization: `Bearer ${accessToken}`} : undefined

        }
    )

    return res.data;
}

export const fetchRecommendedCoursesApi = async (accessToken: string, language: Language): Promise<CoursePreview[]> => {
    const res = await axios.get(`${backendUrl}/api/courses/recommended?language=${language}`, {
        headers: {
            Authorization: `Bearer ${accessToken}`
        }
    });

    return res.data;
}

export const fetchEnrolledCoursesApi = async (accessToken: string, language: Language): Promise<CoursePreviewEnrolled[]> => {
    const res = await axios.get(`${backendUrl}/api/courses/enrolled?language=${language}`, {
        headers: {
            Authorization: `Bearer ${accessToken}`
        }
    });

    return res.data;
}

export const fetchCourseDetailsApi = async (courseId: number, language: Language, signal?: AbortSignal): Promise<CourseDetail> => {
    const res = await axios.get(
        `${backendUrl}/api/courses/${courseId}?language=${language}`,
        {signal}
    );

    return res.data;
}

export const fetchEnrolledCourseLearnApi = async (courseId: number, accessToken: string, language: Language): Promise<CourseLearn> => {
    const res = await axios.get(
        `${backendUrl}/api/courses/enrolled/${courseId}?language=${language}`, {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        });

    return res.data;
}

export const fetchCoursesTopicsApi = async (language: Language): Promise<string[]> => {
    const res = await axios.get(`${backendUrl}/api/courses/topics?language=${language}`);
    return res.data;
}

export const fetchCoursesSkillsApi = async (language: Language): Promise<string[]> => {
    const res = await axios.get(`${backendUrl}/api/courses/skills?language=${language}`);
    return res.data;
}

type CertificateResponse = AxiosResponse<Blob>

export const fetchCourseCertificateApi = async (courseId: number, accessToken: string): Promise<CertificateResponse> => {
    const res = await axios.get(
        `${backendUrl}/api/courses/${courseId}/certificate`, {
            headers: {
                Authorization: `Bearer ${accessToken}`
            },
            responseType: 'blob'
        }
    );
    return res;
}


