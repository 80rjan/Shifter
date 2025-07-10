import React from "react";
import type {UserRegister} from "./UserRegister.tsx";

export interface SelectProps {
    label: string;
    name: keyof UserRegister;
    id: string;
    options?: string[];
    setUser: React.Dispatch<React.SetStateAction<UserRegister>>;
    user: UserRegister;
}