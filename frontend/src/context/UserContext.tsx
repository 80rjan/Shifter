import React, {
    createContext,
    useState,
    type ReactNode,
    type Dispatch,
    type SetStateAction,
    useCallback,
    useEffect,
} from "react";
import {useTranslation} from "react-i18next";
import type {User} from "../models/javaObjects/User.tsx";
import {getUserApi} from "../api/userApi.ts";
import type {Language} from "../models/types/Language.tsx";
import {useAuthContext} from "./AuthContext.tsx";
import {getUserRole} from "../utils/auth.ts";

interface UserContextType {
    user: User | null;
    setUser: Dispatch<SetStateAction<User | null>>;
    isUserLoading: boolean;
    loadUserProfile: (token: string) => Promise<User>;
    useFreeConsultation: () => void;
    removeUser: () => void;
}

export const UserContext = createContext<UserContextType | undefined>(
    undefined
);

export const UserProvider = ({ children }: { children: ReactNode }) => {
    const [user, setUser] = useState<User | null>(null);
    const [isUserLoading, setIsUserLoading] = useState(false);
    const { i18n } = useTranslation();

    const { accessToken, authChecked } = useAuthContext()

    const removeUser = () => {
        setUser(null);
    }

    const useFreeConsultation = () => {
        if (user) {
            setUser({...user, usedFreeConsultation: true});
        }
    }

    const loadUserProfile = useCallback(async (token: string): Promise<User> => {
        setIsUserLoading(true);

        try {
            const userData = await getUserApi(token, i18n.language as Language);
            setUser(userData);
            return userData;
        } catch (error) {
            console.error("Error loading user profile:", error);
            setUser(null);
            throw error;
        } finally {
            setIsUserLoading(false);
        }
    }, [i18n.language]);

    useEffect(() => {
        if (authChecked && accessToken) {
            const role = getUserRole(accessToken);

            // Only load user profile if role is USER
            if (role === 'USER') {
                loadUserProfile(accessToken);
            } else {
                removeUser();
            }
        } else {
            removeUser();
        }
    }, [i18n.language, accessToken, authChecked, loadUserProfile]);


    return (
        <UserContext.Provider
            value={{
                user,
                setUser,
                isUserLoading,
                loadUserProfile,
                useFreeConsultation,
                removeUser
            }}
        >
            {children}
        </UserContext.Provider>
    );
};

export const useUserContext = () => {
    const context = React.useContext(UserContext);
    if (!context) {
        throw new Error("useUserContext must be used within a UserProvider");
    }
    return context;
};