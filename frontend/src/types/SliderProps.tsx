import React from "react";
import type {UserRegister} from "./UserRegister.tsx";

type UserArrayFields = 'interests' | 'desiredSkills';
export interface SliderProps {
    label: string;
    name: UserArrayFields;
    id: string;
    options?: string[];
    setUser: React.Dispatch<React.SetStateAction<UserRegister>>;
    user: UserRegister;
}