import React from "react";
import type {UserPersonalization} from "./javaObjects/UserPersonalization.tsx";

type UserArrayFields = 'interests' | 'desiredSkills';
export interface SliderProps {
    label: string;
    name: UserArrayFields;
    id: string;
    options?: string[];
    setUser: React.Dispatch<React.SetStateAction<UserPersonalization>>;
    user: UserPersonalization;
}