import axios from "axios";

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const fetchPresignedUrlApi = async (accessToken: string, courseId: number, lectureId: number, fileName: string, expirySeconds: number): Promise<string> => {
    const res = await axios.get(
        `${backendUrl}/api/s3/presigned-url?courseId=${courseId}&lectureId=${lectureId}&fileName=${fileName}&expirySeconds=${expirySeconds}`,
        {
            headers: accessToken ? {Authorization: `Bearer ${accessToken}`} : undefined

        }
    )

    return res.data.url;
}