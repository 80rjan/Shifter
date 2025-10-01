export interface User {
    id: number,
    email: string;
    name: string;
    hasUsedFreeConsultation: boolean;
    companySize: string;
    workPosition: string;
    interests: string[];
    skills: string[];
    desiredSkills: string[];
    points: number;
    favoriteCourses: number[];
    isAdmin: boolean;
}