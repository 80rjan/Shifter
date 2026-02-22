
export interface User {
    id: number,
    email: string;
    name: string;
    usedFreeConsultation: boolean;
    companySize: string;
    workPosition: string;
    interests: string[];
    skillsGained: string[];
    points: number;
    favoriteCourseIds: number[];
    profileComplete: boolean;
}