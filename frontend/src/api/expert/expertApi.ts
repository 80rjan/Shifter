import type {Language} from "../../models/types/Language.tsx";
import axios from "axios";
import type {Expert} from "../../models/Expert.tsx";

const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const getExpertApi = async (accessToken: string, language: Language): Promise<Expert> => {
    const res = await axios.get(
        `${backendUrl}/api/experts/me?language=${language}`,
        {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            }
        }
    )

    return res.data;
}