import React, {
    createContext,
    useState,
    useEffect,
    type ReactNode,
    type Dispatch,
    type SetStateAction,
} from "react";
import type {User} from "../types/User.tsx";
import type {UserRegister} from "../types/UserRegister.tsx";
import {loginApi, logoutApi, refreshAccessTokenApi, registerApi} from "../api/auth.ts";
import {useNavigate} from "react-router-dom";

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

export const GlobalProvider = ({children}: { children: ReactNode }) => {
    const [user, setUser] = useState<User | null>(null);
    const [accessToken, setAccessToken] = useState<string | null>(null);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    const register = async (user: UserRegister) => {
        return registerApi(user)
            .then(data => {
                setAccessToken(data.accessToken);
                setUser(data.user);
            })
            .catch(error => {
                setAccessToken(null);
                setUser(null);
                console.log("Registration failed:", error);
                throw error;
            });
    }

    const login = async (email: string, password: string) => {
        return loginApi(email, password)
            .then(data => {
                setAccessToken(data.accessToken);
                setUser(data.user);
            })
            .catch(error => {
                    setAccessToken(null);
                    setUser(null);
                    console.log("Login failed:", error);
                    throw error;
            });
    };

    const logout = async () => {
        return logoutApi()
            .then(() => {
                setAccessToken(null);
                setUser(null);
                navigate("/");
            })
            .catch(err => {
                console.warn("Logout failed:", err);
                throw err;
            });
    };


    const refreshAccessToken = async () => {
        setLoading(true);

        return refreshAccessTokenApi()
            .then(data => {
                setAccessToken(data.accessToken);
                setUser(data.user);
            })
            .catch(error => {
                setAccessToken(null);
                setUser(null);
                console.log("Refresh token failed: ", error);
                throw error;
            })
            .finally(() => {
                setLoading(false);
            })
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
