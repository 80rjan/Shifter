import React, {
    createContext,
    useState,
    useEffect,
    type ReactNode,
    type Dispatch,
    type SetStateAction,
} from "react";
import type {User} from "../models/javaObjects/User.tsx";
import {loginApi, logoutApi, personalizeApi, refreshAccessTokenApi, registerApi, verifyApi} from "../api/authApi.ts";
import {useNavigate} from "react-router-dom";
import type {UserPersonalization} from "../models/javaObjects/UserPersonalization.tsx";

interface AuthContextType {
    user: User | null;
    setUser: Dispatch<SetStateAction<User | null>>;
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
    useFreeConsultation: () => void;
}

export const AuthContext = createContext<AuthContextType | undefined>(
    undefined
);

export const AuthProvider = ({children}: { children: ReactNode }) => {
    const [user, setUser] = useState<User | null>(null);
    const [accessToken, setAccessToken] = useState<string | null>(null);
    const [authChecked, setAuthChecked] = useState<boolean>(false);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    const useFreeConsultation = () => {
        if (user) {
            setUser({...user, hasUsedFreeConsultation: true});
        }
    }

    const register = async (email: string, password: string) => {
        return registerApi(email, password)
            .then(() => {
                console.log("Successfully registered and sent email to user");
            })
            .catch(error => {
                console.log("Registration failed:", error);
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
                console.log("Verification failed:", error);
                throw error;
            });
    }

    const personalize = async (user: UserPersonalization) => {
        return personalizeApi(user)
            .then(data => {
                setAccessToken(data.accessToken);
                setUser(data.user);
            })
            .catch(error => {
                setAccessToken(null);
                setUser(null);
                console.log("Personalization failed:", error);
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
                // console.log(data.accessToken)
                // console.log(encodeURIComponent(Intl.DateTimeFormat().resolvedOptions().timeZone))
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
                setAuthChecked(true);
            })
    };

    useEffect(() => {
        refreshAccessToken();
    }, []);

    return (
        <AuthContext.Provider
            value={{
                user,
                setUser,
                accessToken,
                setAccessToken,
                authChecked,
                setAuthChecked,
                useFreeConsultation,
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
