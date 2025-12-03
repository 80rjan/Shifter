import axios from "axios";

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const verifyApi = async (token: string): Promise<string> => {
    const res = await axios.get(`${backendUrl}/api/verification-tokens/${token}/verify`);

    return res.data;
}

export const verifyEmailApi = async (accessToken: string) => {
    await axios.post(`${backendUrl}/api/verification-tokens/email`,
        {},
        {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            }
        }
    );
}

export const createNewVerificationTokenApi = async (accessToken: string) => {
    const res = await axios.post(`${backendUrl}/api/verification-tokens`,
        {},
        {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            }
        }
    )

    return res.data;
}