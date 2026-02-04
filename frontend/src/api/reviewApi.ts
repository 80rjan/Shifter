import axios from "axios";
import type {Review} from "../models/javaObjects/Review.tsx";

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const writeReviewApi = async (
    accessToken: string,
    courseId: number,
    rating: number,
    comment: string,
): Promise<void> => {

    await axios.post(
        `${backendUrl}/api/review/${courseId}`,
        {
            rating,
            comment
        },
        {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        }
    );
}

export const updateReviewApi = async (
    accessToken: string,
    courseId: number,
    rating: number,
    comment: string,
): Promise<void> => {

    await axios.put(
        `${backendUrl}/api/review/${courseId}`,
        {
            rating,
            comment
        },
        {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        }
    );
}

export const getReviewApi = async (
    accessToken: string,
    courseId: number,
): Promise<Review> => {

    const  res = await axios.get(
        `${backendUrl}/api/review/course/${courseId}`,
        {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        }
    );

    return res.data;
}