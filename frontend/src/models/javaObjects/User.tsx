
export interface User {
    id: number,
    email: string;
    name: string;
    hasUsedFreeConsultation: boolean;
    companySize: string;
    workPosition: string;
    interests: string[];
    skillsGained: string[];
    points: number;
    favoriteCourses: number[];
    isAdmin: boolean;
    isProfileComplete: boolean;
}