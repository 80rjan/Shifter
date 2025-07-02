import axios from "axios";

const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const checkEmailExistsApi = async (email: string): Promise<boolean> => {
    const res = await axios.get(`${backendUrl}/api/auth/check-email`, {
        params: {
            email: email,
        },
    });

    return res.data;
}