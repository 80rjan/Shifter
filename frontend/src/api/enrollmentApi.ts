import axios from "axios";
import type {Enrollment} from "../types/Enrollment.tsx";

const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const enrollUserApi = async (courseId: number, accessToken: string): Promise<void> => {
    await axios.post(
        `${backendUrl}/api/enrollments/create/${courseId}`,
        {},
        {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            }
        }
    )

}

export const fetchUserEnrollmentsApi = async (accessToken: string): Promise<Enrollment[]> => {
    const res = await axios.get(
        `${backendUrl}/api/enrollments/user`,
        {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            }
        }
    )

    return res.data;
}