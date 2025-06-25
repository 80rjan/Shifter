import React, {useEffect} from "react";
import type {User} from "../../types/User.tsx";
import RegisterSlider from "../inputs/RegisterSlider.tsx";

function RegisterStepThree({setUser, user, setCanContinue, setError}:
                       {
                           setUser: React.Dispatch<React.SetStateAction<User>>,
                           user: User,
                           setCanContinue: React.Dispatch<React.SetStateAction<boolean>>,
                           setError: React.Dispatch<React.SetStateAction<string>>
                       }) {
    const interests = [
        "Sales Strategies",
        "Marketing",
        "Leadership",
        "Management",
        "Digital Transformation",
        "Business Transformation",
        "Entrepreneurship",
        "Startup",
        "Sales",
        "Negotiation",
        "Finance For Business"
    ]

    useEffect(() => {
        if (user.interests.length === 0) {
            setError("Please ensure all inputs are completed.");
            setCanContinue(false);
        } else {
            setError("");
            setCanContinue(true);
        }
    }, [user.interests]);

    return (
        <div className="flex flex-col gap-4 w-full items-center">
            <RegisterSlider
                label={"Pick Your Preferences"}
                name={"interests"}
                id={"interests"}
                options={interests}
                setUser={setUser}
                user={user}
            />
        </div>
    )
}

export default RegisterStepThree;