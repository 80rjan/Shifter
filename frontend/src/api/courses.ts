import type {FilterParams} from "../types/FilterParams.tsx";
import type {Course} from "../types/Course.tsx";
import axios from "axios";
import qs from 'qs';

const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const fetchFilteredCoursesApi = async (params: FilterParams, signal?: AbortSignal): Promise<Course[]> => {
    const res = await axios.get(
        `${backendUrl}/api/courses`,
        {
            params,
            paramsSerializer: params => qs.stringify(params, {arrayFormat: 'repeat'}),
            signal
        }
    )

    return res.data;
}

export const fetchAllCoursesApi = async (signal?: AbortSignal): Promise<Course[]> => {
    const res = await axios.get(
        `${backendUrl}/api/courses`,
        {
            params: {},
            paramsSerializer: params => qs.stringify(params, {arrayFormat: 'repeat'}),
            signal
        }
    )

    return res.data;
}

export const fetchRecommendedCoursesApi = async (accessToken: string): Promise<Course[]> => {
    const res = await axios.get(`${backendUrl}/api/courses/recommended`, {
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


