import axios from "axios";
import type {UserRegister} from "../types/UserRegister.tsx";
import type {User} from "../types/User.tsx";

const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const refreshAccessTokenApi = async (): Promise<{user: User, accessToken: string}> => {
    const res = await axios.post(`${backendUrl}/api/auth/refresh`,
        {},
        {withCredentials: true}
    );

    return res.data;
}

export const registerApi = async (user: UserRegister): Promise<{user: User, accessToken: string}> => {
    const res = await axios.post(`${backendUrl}/api/auth/register`,
        user,
        { withCredentials: true }
    );

    return res.data;
}

export const loginApi = async (email: string, password: string): Promise<{user: User, accessToken: string}> => {
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