import React, {useEffect} from "react";
import type {User} from "../../types/User.tsx";
import RegisterInput from "../inputs/RegisterInput.tsx";
import {isValidEmail, hasUppercase, hasDigit, hasSpecialChar} from "../../utils/validation.tsx";

function RegisterStepOne({setUser, user, setCanContinue, setError}: {
    setUser: React.Dispatch<React.SetStateAction<User>>,
    user: User,
    setCanContinue: React.Dispatch<React.SetStateAction<boolean>>,
    setError: React.Dispatch<React.SetStateAction<string>>
}) {
    const [showPassword, setShowPassword] = React.useState(false);

    useEffect(() => {
        if (!user.email || !user.password || !user.passwordConfirmation) {
            setError("Please ensure all inputs are completed.");
            setCanContinue(false);
        } else if (user.email && !isValidEmail(user.email)) {
            setError("Email must be valid");
            setCanContinue(false);
        } else if (user.password && user.passwordConfirmation && user.password !== user.passwordConfirmation) {
            setError("Passwords do not match");
            setCanContinue(false);
        } else if (!hasUppercase(user.password)) {
            setError("Password must contain at least one uppercase letter");
            setCanContinue(false);
        } else if (!hasDigit(user.password)) {
            setError("Password must contain at least one digit");
            setCanContinue(false);
        } else if (!hasSpecialChar(user.password)) {
            setError("Password must contain at least one special character");
            setCanContinue(false);
        } else {
            setError("");
            setCanContinue(true);
        }

    }, [user.email, user.password, user.passwordConfirmation])

    return (
        <div className="flex flex-col gap-4 w-full items-center">
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
        </div>
    );
}

export default RegisterStepOne;
