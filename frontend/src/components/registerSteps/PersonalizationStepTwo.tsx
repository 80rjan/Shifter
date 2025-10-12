import React, {useEffect} from "react";
import type {UserPersonalization} from "../../models/javaObjects/UserPersonalization.tsx";
import PersonalizationSlider from "../inputs/PersonalizationSlider.tsx";
import {fetchCoursesTopicsApi} from "../../api/courseApi.ts";
import {useTranslation} from "react-i18next";

function PersonalizationStepTwo({setUser, user, setError}: {
    setUser: React.Dispatch<React.SetStateAction<UserPersonalization>>,
    user: UserPersonalization,
    setError: React.Dispatch<React.SetStateAction<string>>,
}) {
    const [interests, setInterests] = React.useState<string[]>([]);
    const { t } = useTranslation("personalize");

    useEffect(() => {
        const fetchCourseTopics = async () => {
            try {
                const topicsData = await fetchCoursesTopicsApi();
                setInterests(topicsData);
            } catch (err) {
                console.error("Failed to fetch javaObjects topics (user interests)", err);
            }
        };

        fetchCourseTopics();
    }, []);

    useEffect(() => {
        if (user.interests.length === 0) {
            setError(t("stepTwo.errorNoInterest"));
        } else {
            setError("");
        }
    }, [user.interests]);


    return (
        <section
            className="flex flex-col gap-4 w-full items-center">
            {
                interests.length > 0 &&
                <PersonalizationSlider
                    label={t("stepTwo.interests")}
                    name={"interests"}
                    id={"interests"}
                    options={interests}
                    setUser={setUser}
                    user={user}
                />
            }
        </section>
    )
}

export default PersonalizationStepTwo;