import axios from "axios";

const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const toggleFavoriteCourseApi = async (courseId: number, accessToken: string): Promise<void> => {
    await axios.put(
        `${backendUrl}/api/user/favorite-course/${courseId}`,
        {},
        {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            },
            withCredentials: true
        }
    )
}