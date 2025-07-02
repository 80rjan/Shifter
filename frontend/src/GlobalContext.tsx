import React, {
    createContext,
    useState,
    useEffect,
    type ReactNode,
    type Dispatch,
    type SetStateAction,
} from "react";
import axios from "axios";
import type { User } from "./types/User.tsx";
import type {UserRegister} from "./types/UserRegister.tsx";

interface GlobalContextType {
    user: User | null;
    setUser: Dispatch<SetStateAction<User | null>>;
    accessToken: string | null;
    setAccessToken: Dispatch<SetStateAction<string | null>>;
    register: (user: UserRegister) => Promise<void>;
    login: (email: string, password: string) => Promise<void>;
    logout: () => void;
    refreshAccessToken: () => Promise<void>;
    loading: boolean;
}

export const GlobalContext = createContext<GlobalContextType | undefined>(
    undefined
);

export const GlobalProvider = ({ children }: { children: ReactNode }) => {
    const [user, setUser] = useState<User | null>(null);
    const [accessToken, setAccessToken] = useState<string | null>(null);
    const [loading, setLoading] = useState(true);

    const backendUrl = import.meta.env.VITE_BACKEND_URL;

    const register = async (user: UserRegister) => {
        try {
            const res = await axios.post(
                `${backendUrl}/api/auth/register`,
                user,
                { withCredentials: true }
            );
            setAccessToken(res.data.accessToken);
            setUser(res.data.user);
        } catch (error) {
            setAccessToken(null);
            setUser(null);
            console.log("Registration failed:", error);
            throw error;
        }
    }

    const login = async (email: string, password: string) => {
        try {
            const res = await axios.post(
                `${backendUrl}/api/auth/authenticate`,
                { email, password },
                { withCredentials: true }
            );
            setAccessToken(res.data.accessToken);
            setUser(res.data.user);
        } catch (error) {
            setAccessToken(null);
            setUser(null);
            console.log("Login failed:", error);
            throw error;
        }
    };

    const logout = async () => {
        try {
            await axios.post(`${backendUrl}/api/auth/logout`, {}, { withCredentials: true });
        } catch (err) {
            console.warn("Logout failed:", err);
        } finally {
            setAccessToken(null);
            setUser(null);
        }
    };


    const refreshAccessToken = async () => {
        setLoading(true);
        try {
            const res = await axios.post(`${backendUrl}/api/auth/refresh`, {}, { withCredentials: true });
            setAccessToken(res.data.accessToken);
            setUser(res.data.user);
        } catch (error) {
            setAccessToken(null);
            setUser(null);
            console.log("Refresh token failed: ", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        refreshAccessToken();
    }, []);

    return (
        <GlobalContext.Provider
            value={{
                user,
                setUser,
                accessToken,
                setAccessToken,
                register,
                login,
                logout,
                refreshAccessToken,
                loading,
            }}
        >
            {children}
        </GlobalContext.Provider>
    );
};

// Custom hook for ease of use
export const useGlobalContext = () => {
    const context = React.useContext(GlobalContext);
    if (!context) {
        throw new Error("useGlobalContext must be used within a GlobalProvider");
    }
    return context;
};
