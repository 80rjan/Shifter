export function hasDigit(str: string) {
    return /\d/.test(str);
}

export function hasUppercase(str: string) {
    return /[A-Z]/.test(str);
}

export function hasSpecialChar(str: string) {
    return /[!@#$%^&*(),.?":{}|<>]/.test(str);
}

export function isValidEmail(email: string) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}
