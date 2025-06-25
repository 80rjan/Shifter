import React from "react";
import type {User} from "./User.tsx";

export interface SelectProps {
    label: string;
    name: string;
    id: string;
    options?: string[];
    setUser: React.Dispatch<React.SetStateAction<User>>;
    user: User;
}