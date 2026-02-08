import axios from "axios";
import type {Enrollment} from "../models/javaObjects/Enrollment.tsx";


const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const enrollUserApi = async (courseId: number, accessToken: string): Promise<Enrollment> => {
    const res = await axios.post(
        `${backendUrl}/api/enrollments/create/${courseId}`,
        {},
        {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            }
        }
    )

    return res.data;
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