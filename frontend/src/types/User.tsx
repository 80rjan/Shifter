export interface User {
    id: number,
    email: string;
    name: string;
    companyType: string;
    workPosition: string;
    interests: string[];
    skills: string[];
    desiredSkills: string[];
    points: number;
    favoriteCourses: number[];
}