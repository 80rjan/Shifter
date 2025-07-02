export interface UserRegister {
    email: string;
    password: string;
    passwordConfirmation: string;
    name: string;
    workPosition: string;
    companyType: string;
    interests: string[];
    skills: string[];
    skillsGap: string[];
}