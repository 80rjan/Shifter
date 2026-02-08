import React, {useEffect} from "react";
import type {UserPersonalization} from "../../models/javaObjects/UserPersonalization.tsx";
import PersonalizationSlider from "../inputs/PersonalizationSlider.tsx";
import {fetchCoursesSkillsApi} from "../../api/courseApi.ts";
import {useTranslation} from "react-i18next";
import type {Language} from "../../models/types/Language.tsx";

function PersonalizationStepThree({setUser, user, setError}: {
    setUser: React.Dispatch<React.SetStateAction<UserPersonalization>>,
    user: UserPersonalization,
    setError: React.Dispatch<React.SetStateAction<string>>,
}){
    const [skills, setSkills] = React.useState<string[]>([]);
    const { i18n, t } = useTranslation("personalize");

    useEffect(() => {
        const fetchSkills = async () => {
            try {
                const skillsData = await fetchCoursesSkillsApi(i18n.language as Language);
                setSkills(skillsData);
            } catch (err) {
                console.error("Failed to fetch skills", err);
            }
        };

        fetchSkills();
    }, []);

    useEffect(() => {
        if (user.tagIdList.length === 0) {
            setError(t("stepThree.errorNoSkill"));
        } else {
            setError("");
        }
    }, [user.tagIdList]);

    return (
        <section
            className="flex flex-col gap-4 w-full">
            {
                skills.length > 0 &&
                <PersonalizationSlider
                    label={t("stepThree.desiredSkills")}
                    id={"desired-skills"}
                    allOptions={skills}
                    setUser={setUser}
                    user={user}
                />
            }
        </section>
    )
}

export default PersonalizationStepThree;