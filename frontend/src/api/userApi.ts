import axios from "axios";
import type {User} from "../models/javaObjects/User.tsx";
import type {Language} from "../models/types/Language.tsx";


const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const toggleFavoriteCourseApi = async (courseId: number, accessToken: string): Promise<void> => {
    await axios.put(
        `${backendUrl}/api/users/favorite-course/${courseId}`,
        {},
        {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            }
        }
    )
}

export const updateUserApi = async (userInfo: {name: string, workPosition: string, companySize: string}, accessToken: string): Promise<void> => {
    await axios.put(
        `${backendUrl}/api/users/update/info`,
        userInfo,
        {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            }
        }
    )
}

export const updateUserTagsApi = async (tagIdList: number[], accessToken: string): Promise<void> => {
    await axios.put(
        `${backendUrl}/api/users/update/tags`,
        tagIdList,
        {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            }
        }
    )
}

export const getUserApi = async (accessToken: string, language: Language): Promise<User> => {
    const res = await axios.get(
        `${backendUrl}/api/users/me?language=${language}`,
        {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            }
        }
    )

    return res.data;
}