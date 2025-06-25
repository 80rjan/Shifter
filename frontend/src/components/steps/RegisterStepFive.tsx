import React, {useEffect} from "react";
import type {User} from "../../types/User.tsx";
import RegisterSlider from "../inputs/RegisterSlider.tsx";

function RegisterStepFive({setUser, user, setCanContinue, setError}: {
    setUser: React.Dispatch<React.SetStateAction<User>>,
    user: User,
    setCanContinue: React.Dispatch<React.SetStateAction<boolean>>,
    setError: React.Dispatch<React.SetStateAction<string>>
}) {
    const skills = [
        "Strategic Thinking",
        "Strategic Planning",
        "Business Development",
        "Project Management",
        "Operation Management",
        "Logistics",
        "Business Model Creation",
        "Risk Management",
        "Startup Methodologies",

        "Marketing",
        "Digital Marketing",
        "Traditional Marketing",
        "Branding",
        "Sales",
        "Sales Techniques",
        "B2b Tactics",
        "B2c Tactics",
        "Seo",
        "Google Analytics",
        "Ecommerce Platforms",

        "Leadership",
        "People Management",
        "Team Building",
        "Delegation",
        "Conflict Resolution",
        "Decision Making",
        "Performance Evaluation",
        "Change Management",

        "Communication",
        "Emotional Intelligence",
        "Time Management",
        "Adaptability",
        "Critical Thinking",
        "Creativity",
        "Problem Solving",

        "Opportunity Identification",
        "Fundraising Capital",
        "Innovation Management",

        "Ai Tools",

        "Business Law",
        "Business Ethics"
    ];

    useEffect(() => {
        if (user.skillsGap.length === 0) {
            setError("Please ensure all inputs are completed.");
            setCanContinue(false);
        } else {
            setError("");
            setCanContinue(true);
        }
    }, [user.skillsGap]);

    return (
        <div className="flex flex-col gap-4 w-full">
            <RegisterSlider
                label={"Identify Skills Gap"}
                name={"skillsGap"}
                id={"skills-gap"}
                options={skills}
                setUser={setUser}
                user={user}
            />
        </div>
    )
}

export default RegisterStepFive;