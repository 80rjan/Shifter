import React, {useEffect} from "react";
import type {UserRegister} from "../../types/UserRegister.tsx";
import RegisterSlider from "../inputs/RegisterSlider.tsx";
import {fetchCoursesSkillsApi} from "../../api/courseApi.ts";

function RegisterStepFour({setUser, user, setError}: {
    setUser: React.Dispatch<React.SetStateAction<UserRegister>>,
    user: UserRegister,
    setError: React.Dispatch<React.SetStateAction<string>>,
}) {
    const [skills, setSkills] = React.useState<string[]>([]);

    useEffect(() => {
        const fetchSkills = async () => {
            try {
                const skillsData = await fetchCoursesSkillsApi();
                setSkills(skillsData);
            } catch (err) {
                console.error("Failed to fetch skills", err);
            }
        };

        fetchSkills();
    }, []);

    useEffect(() => {
        if (user.skills.length === 0) {
            setError("Tell us what you're great at — choose at least one strength");
        } else {
            setError("");
        }
    }, [user.skills]);


    return (
        <section
            className="flex flex-col gap-4 w-full">
            {
                skills.length > 0 &&
                <RegisterSlider
                    label={"Identify Strengths"}
                    name={"skills"}
                    id={"skills"}
                    options={skills}
                    setUser={setUser}
                    user={user}
                />
            }
        </section>
    )
}

export default RegisterStepFour;