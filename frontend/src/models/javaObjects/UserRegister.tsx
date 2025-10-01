export interface UserRegister {
    email: string;
    password: string;
    passwordConfirmation: string;
    name: string;
    workPosition: string;
    companySize: string;
    interests: string[];
    desiredSkills: string[];
}