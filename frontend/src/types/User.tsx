export interface User {
    id: number,
    email: string;
    name: string;
    companyType: string;
    workPosition: string;
    interests: string[];
    skills: string[];
    skillGap: string[];
    points: number;
    favoriteCourses: number[];
}