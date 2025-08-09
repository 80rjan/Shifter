import React, {useEffect} from "react";
import type {UserRegister} from "../../models/javaObjects/UserRegister.tsx";
import RegisterInput from "../inputs/RegisterInput.tsx";
import {isValidEmail, hasUppercase, hasDigit, hasSpecialChar} from "../../utils/validation.ts";

function RegisterStepOne({setUser, user, setError}: {
    setUser: React.Dispatch<React.SetStateAction<UserRegister>>,
    user: UserRegister,
    setError: React.Dispatch<React.SetStateAction<string>>,
}) {
    const [showPassword, setShowPassword] = React.useState(false);

    useEffect(() => {
        if (!user.email || !user.password || !user.passwordConfirmation) {
            setError("Please ensure all inputs are completed.");
        } else if (user.email && !isValidEmail(user.email)) {
            setError("Email must be valid");
        } else if (user.password && user.passwordConfirmation && user.password !== user.passwordConfirmation) {
            setError("Passwords do not match");
        } else if (!hasUppercase(user.password)) {
            setError("Password must contain at least one uppercase letter");
        } else if (!hasDigit(user.password)) {
            setError("Password must contain at least one digit");
        } else if (!hasSpecialChar(user.password)) {
            setError("Password must contain at least one special character");
        } else {
            setError("");
        }

    }, [user.email, user.password, user.passwordConfirmation])

    return (
        <section
            className="flex flex-col gap-4 w-full items-center">
            <RegisterInput
                placeholder={"name@email.com"}
                label={"Email address"}
                name={"email"}
                type={"email"}
                id={"email"}
                setUser={setUser}
                user={user}
            />
            <RegisterInput
                placeholder={"********"}
                label={"Password"}
                name={"password"}
                type={"password"}
                id={"password"}
                showPassword={showPassword}
                setShowPassword={setShowPassword}
                setUser={setUser}
                user={user}
            />
            <RegisterInput
                placeholder={"********"}
                label={"Confirm password"}
                name={"passwordConfirmation"}
                type={"password"}
                id={"password-confirmation"}
                showPassword={showPassword}
                setShowPassword={setShowPassword}
                setUser={setUser}
                user={user}
            />
        </section>
    );
}

export default RegisterStepOne;
