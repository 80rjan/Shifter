import React from "react";
import type {User} from "./User.tsx";

type UserArrayFields = 'interests' | 'skills' | 'skillsGap';
export interface SliderProps {
    label: string;
    name: UserArrayFields;
    id: string;
    options?: string[];
    setUser: React.Dispatch<React.SetStateAction<User>>;
    user: User;
}