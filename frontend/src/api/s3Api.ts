import axios from "axios";
import type {Language} from "../models/types/Language.tsx";

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const fetchPresignedUrlApi = async (accessToken: string, lectureId: number, language: Language, expirySeconds: number): Promise<string> => {
    const res = await axios.get(
        `${backendUrl}/api/s3/presigned-url?&lectureId=${lectureId}&language=${language}&expirySeconds=${expirySeconds}`,
        {
            headers: accessToken ? {Authorization: `Bearer ${accessToken}`} : undefined

        }
    )

    return res.data.url;
}