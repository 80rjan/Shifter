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
        `${backendUrl}/api/users/update/info`,
        userInfo,
        {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            }
        }
    )
}

export const updateUserInterestsApi = async (interests: string[], accessToken: string): Promise<void> => {
    await axios.put(
        `${backendUrl}/api/users/update/interests`,
        interests,
        {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            }
        }
    )
}

export const updateUserDesiredSkillsApi = async (desiredSkills: string[], accessToken: string): Promise<void> => {
    await axios.put(
        `${backendUrl}/api/users/update/desired-skills`,
        desiredSkills,
        {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            }
        }
    )
}