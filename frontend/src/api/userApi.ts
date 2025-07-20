import axios from "axios";

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

export const updateUserApi = async (userInfo: {name: string, workPosition: string, companyType: string}, accessToken: string): Promise<void> => {
    await axios.put(
        `${backendUrl}/api/users/update`,
        userInfo,
        {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            }
        }
    )
}