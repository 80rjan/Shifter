import React, {useEffect, useState} from "react";
import {useAuthContext} from "../context/AuthContext.tsx";
import {hasDigit, hasSpecialChar, hasUppercase, isValidEmail} from "../utils/validation.ts";
import {checkEmailExistsApi} from "../api/authApi.ts";

export function useRegisterHook() {
    const {register} = useAuthContext();
    const [email, setEmail] = React.useState("");
    const [password, setPassword] = React.useState("");
    const [passwordConfirmation, setPasswordConfirmation] = React.useState("");
    const [showError, setShowError] = useState(false);
    const [error, setError] = React.useState("");
    const [isSuccess, setIsSuccess] = React.useState(false);
    const [isLoading, setIsLoading] = React.useState<boolean>(false);
    const [showPassword, setShowPassword] = React.useState(false);

    useEffect(() => {
        if (!email || !password || !passwordConfirmation) {
            setError("Please ensure all inputs are completed.");
        } else if (email && !isValidEmail(email)) {
            setError("Email must be valid");
        } else if (password && passwordConfirmation && password !== passwordConfirmation) {
            setError("Passwords do not match");
        } else if (!hasUppercase(password)) {
            setError("Password must contain at least one uppercase letter");
        } else if (!hasDigit(password)) {
            setError("Password must contain at least one digit");
        } else if (!hasSpecialChar(password)) {
            setError("Password must contain at least one special character");
        } else {
            setError("");
        }

    }, [email, password, passwordConfirmation])

    const handleRegister = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        if (error.length > 0) {
            setShowError(true);
            return;
        }

        setIsLoading(true);
        try {
            const exists = await checkEmailExistsApi(email);

            if (exists) {
                setError("This email is already registered. Please sign in or use a different email.");
                return;
            }

            await register(email, password);
            setIsSuccess(true);
        } catch (err) {
            console.error("Error checking email:", err);
            setError("An unexpected error occurred. Please try again later or contact us contact us directly at support@mail.shift-er.com");
        } finally {
            setIsLoading(false);
        }
    }

    return {
        email,
        setEmail,
        password,
        setPassword,
        passwordConfirmation,
        setPasswordConfirmation,
        showPassword,
        setShowPassword,
        showError,
        error,
        isSuccess,
        isLoading,
        handleRegister
    }
}