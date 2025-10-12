import axios from "axios";
import type {UserPersonalization} from "../models/javaObjects/UserPersonalization.tsx";
import type {User} from "../models/javaObjects/User.tsx";

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const refreshAccessTokenApi = async (): Promise<{ user: User, accessToken: string }> => {
    const res = await axios.post(`${backendUrl}/api/auth/refresh`,
        {},
        {withCredentials: true}
    );

    return res.data;
}

export const registerApi = async (email: string, password: string): Promise<void> => {
    await axios.post(`${backendUrl}/api/auth/register`,
        {email, password},
        {withCredentials: true}
    );
}

export const verifyApi = async (token: string): Promise<string> => {
    const res = await axios.post(`${backendUrl}/api/auth/verify`,
        {token},
        {withCredentials: true}
    );

    return res.data;
}

export const personalizeApi = async (user: UserPersonalization): Promise<{ user: User, accessToken: string }> => {
    const res = await axios.post(`${backendUrl}/api/auth/personalize`,
        user,
        {withCredentials: true}
    );

    return res.data;
}

export const oAuthLoginApi = async (accessToken: string): Promise<{ user: User, accessToken: string }> => {
    const res = await axios.get(`${backendUrl}/api/auth/oauth/finalize`, {
            withCredentials: true,
            headers: {
                Authorization: `Bearer ${accessToken}`
            },
        }
    );

    return res.data;
}

export const loginApi = async (email: string, password: string): Promise<{ user: User, accessToken: string }> => {
    const res = await axios.post(`${backendUrl}/api/auth/authenticate`,
        {email, password},
        {withCredentials: true}
    );

    return res.data;
}

export const logoutApi = async (): Promise<void> => {
    await axios.post(`${backendUrl}/api/auth/logout`,
        {},
        {withCredentials: true}
    );
}

export const checkEmailExistsApi = async (email: string): Promise<boolean> => {
    const res = await axios.get(`${backendUrl}/api/auth/check-email`, {
        params: {
            email: email,
        },
    });

    return res.data;
}