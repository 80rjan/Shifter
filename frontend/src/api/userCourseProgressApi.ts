import axios from "axios";
import type {UserCourseProgress} from "../models/javaObjects/UserCourseProgress.tsx";


const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const completeLectureApi = async (progressId: number, accessToken: string): Promise<UserCourseProgress> => {
    const res = await axios.put(
        `${backendUrl}/api/progress/${progressId}/complete`,
        null,
        {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            }
        }
    )

    return res.data;
}

export const uncompleteLectureApi = async (progressId: number, accessToken: string): Promise<UserCourseProgress> => {
    const res = await axios.put(
        `${backendUrl}/api/progress/${progressId}/uncomplete`,
        null,
        {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            }
        }
    )

    return res.data;
}