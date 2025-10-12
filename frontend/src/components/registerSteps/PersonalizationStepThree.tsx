import React, {useEffect} from "react";
import type {UserPersonalization} from "../../models/javaObjects/UserPersonalization.tsx";
import PersonalizationSlider from "../inputs/PersonalizationSlider.tsx";
import {fetchCoursesSkillsApi} from "../../api/courseApi.ts";
import {useTranslation} from "react-i18next";

function PersonalizationStepThree({setUser, user, setError}: {
    setUser: React.Dispatch<React.SetStateAction<UserPersonalization>>,
    user: UserPersonalization,
    setError: React.Dispatch<React.SetStateAction<string>>,
}){
    const [skills, setSkills] = React.useState<string[]>([]);
    const { t } = useTranslation("personalize");

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
        if (user.desiredSkills.length === 0) {
            setError(t("stepThree.errorNoSkill"));
        } else {
            setError("");
        }
    }, [user.desiredSkills]);

    return (
        <section
            className="flex flex-col gap-4 w-full">
            {
                skills.length > 0 &&
                <PersonalizationSlider
                    label={t("stepThree.desiredSkills")}
                    name={"desiredSkills"}
                    id={"desired-skills"}
                    options={skills}
                    setUser={setUser}
                    user={user}
                />
            }
        </section>
    )
}

export default PersonalizationStepThree;