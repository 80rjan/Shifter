// src/context/ExpertContext.tsx
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
import type {Language} from "../models/types/Language.tsx";
import {useAuthContext} from "./AuthContext.tsx";
import type {Expert} from "../models/Expert.tsx";
import {getExpertApi} from "../api/expert/expertApi.ts";
import {getUserRole} from "../utils/auth.ts";

interface ExpertContextType {
    expert: Expert | null;
    setExpert: Dispatch<SetStateAction<Expert | null>>;
    isExpertLoading: boolean;
    loadExpertProfile: (token: string) => Promise<Expert>;
    removeExpert: () => void;
}

export const ExpertContext = createContext<ExpertContextType | undefined>(
    undefined
);

export const ExpertProvider = ({ children }: { children: ReactNode }) => {
    const [expert, setExpert] = useState<Expert | null>(null);
    const [isExpertLoading, setIsExpertLoading] = useState(false);
    const { i18n } = useTranslation();

    const { accessToken, authChecked } = useAuthContext();

    const removeExpert = () => {
        setExpert(null);
    }

    const loadExpertProfile = useCallback(async (token: string): Promise<Expert> => {
        setIsExpertLoading(true);

        try {
            const expertData = await getExpertApi(token, i18n.language as Language);
            setExpert(expertData);
            return expertData;
        } catch (error) {
            console.error("Error loading expert profile:", error);
            setExpert(null);
            throw error;
        } finally {
            setIsExpertLoading(false);
        }
    }, [i18n.language]);

    useEffect(() => {
        if (authChecked && accessToken) {
            const role = getUserRole(accessToken);

            // Only load expert profile if role is EXPERT
            if (role === 'EXPERT') {
                loadExpertProfile(accessToken);
            } else {
                removeExpert();
            }
        } else {
            removeExpert();
        }
    }, [i18n.language, accessToken, authChecked, loadExpertProfile]);

    return (
        <ExpertContext.Provider
            value={{
                expert,
                setExpert,
                isExpertLoading,
                loadExpertProfile,
                removeExpert
            }}
        >
            {children}
        </ExpertContext.Provider>
    );
};

export const useExpertContext = () => {
    const context = React.useContext(ExpertContext);
    if (!context) {
        throw new Error("useExpertContext must be used within an ExpertProvider");
    }
    return context;
};