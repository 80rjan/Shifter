import React from "react";
import type {UserPersonalization} from "./javaObjects/UserPersonalization.tsx";

export interface SelectProps {
    label: string;
    name: keyof UserPersonalization;
    id: string;
    options?: string[];
    setUser: React.Dispatch<React.SetStateAction<UserPersonalization>>;
    user: UserPersonalization;
}