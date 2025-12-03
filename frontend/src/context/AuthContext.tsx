import React, {
    createContext,
    useState,
    useEffect,
    type ReactNode,
    type Dispatch,
    type SetStateAction,
} from "react";
import {loginApi, logoutApi, personalizeApi, refreshAccessTokenApi, registerApi} from "../api/authApi.ts";

import {useNavigate} from "react-router-dom";
import type {UserPersonalization} from "../models/javaObjects/UserPersonalization.tsx";
import {verifyApi} from "../api/verificationTokenApi.ts";

interface AuthContextType {
    accessToken: string | null;
    setAccessToken: Dispatch<SetStateAction<string | null>>;
    authChecked: boolean;
    setAuthChecked: Dispatch<SetStateAction<boolean>>;
    register: (email: string, password: string) => Promise<void>;
    verify: (verificationToken: string) => Promise<string>;
    personalize: (user: UserPersonalization) => Promise<void>;
    login: (email: string, password: string) => Promise<void>;
    logout: () => void;
    refreshAccessToken: () => Promise<void>;
    loading: boolean;
}

export const AuthContext = createContext<AuthContextType | undefined>(
    undefined
);

export const AuthProvider = ({children}: { children: ReactNode }) => {
    const [accessToken, setAccessToken] = useState<string | null>(null);
    const [authChecked, setAuthChecked] = useState<boolean>(false);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    const register = async (email: string, password: string) => {
        return registerApi(email, password)
            .then(() => {
                console.log("Successfully registered and sent email to user");
            })
            .catch(error => {
                console.error("Registration failed:", error);
                throw error;
            });
    }

    const verify = async (verificationToken: string) => {
        return verifyApi(verificationToken)
            .then(userEmail => {
                console.log("Successfully verified user email");
                return userEmail;
            })
            .catch(error => {
                console.error("Verification failed:", error);
                throw error;
            });
    }

    const personalize = async (user: UserPersonalization) => {
        return personalizeApi(user)
            .then(async data => {
                setAccessToken(data.accessToken);
            })
            .catch(error => {
                setAccessToken(null);
                console.error("Personalization failed:", error);
                throw error;
            });
    }

    const login = async (email: string, password: string) => {
        return loginApi(email, password)
            .then(async data => {
                setAccessToken(data.accessToken);
            })
            .catch(error => {
                setAccessToken(null);
                console.error("Login failed:", error);
                throw error;
            });
    };

    const logout = async () => {
        return logoutApi()
            .then(() => {
                setAccessToken(null);
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
            .then(async data => {
                console.log("ACCESS TOKEN", data.accessToken)
                setAccessToken(data.accessToken);
            })
            .catch(error => {
                setAccessToken(null);
                console.error("Refresh token failed: ", error);
            })
            .finally(() => {
                setLoading(false);
                setAuthChecked(true);
            })
    };


    useEffect(() => {
        refreshAccessToken();
    }, []);

    return (
        <AuthContext.Provider
            value={{
                accessToken,
                setAccessToken,
                authChecked,
                setAuthChecked,
                register,
                verify,
                personalize,
                login,
                logout,
                refreshAccessToken,
                loading,
            }}
        >
            {children}
        </AuthContext.Provider>
    );
};

// Custom hook for ease of use
export const useAuthContext = () => {
    const context = React.useContext(AuthContext);
    if (!context) {
        throw new Error("useGlobalContext must be used within a GlobalProvider");
    }
    return context;
};
