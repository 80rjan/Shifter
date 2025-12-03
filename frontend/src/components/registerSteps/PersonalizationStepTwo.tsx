import React, {useEffect} from "react";
import type {UserPersonalization} from "../../models/javaObjects/UserPersonalization.tsx";
import PersonalizationSlider from "../inputs/PersonalizationSlider.tsx";
import {fetchCoursesTopicsApi} from "../../api/courseApi.ts";
import {useTranslation} from "react-i18next";
import type {Language} from "../../models/types/Language.tsx";
import PersonalizationSliderSkeleton from "../skeletons/PersonalizationSliderSkeleton.tsx";

function PersonalizationStepTwo({setUser, user, setError}: {
    setUser: React.Dispatch<React.SetStateAction<UserPersonalization>>,
    user: UserPersonalization,
    setError: React.Dispatch<React.SetStateAction<string>>,
}) {
    const [interests, setInterests] = React.useState<string[]>([]);
    const [loading, setLoading] = React.useState(true);
    const {t} = useTranslation("personalize");
    const {i18n} = useTranslation();

    useEffect(() => {
        const fetchCourseTopics = async () => {
            setLoading(true);
            try {
                const topicsData = await fetchCoursesTopicsApi(i18n.language as Language);
                setInterests(topicsData);
            } catch (err) {
                console.error("Failed to fetch course topics (user interests)", err);
            } finally {
                setLoading(false);
            }
        };

        fetchCourseTopics();
    }, [i18n.language]);

    useEffect(() => {
        if (user.attributeIdList.length === 0) {
            setError(t("stepTwo.errorNoInterest"));
        } else {
            setError("");
        }
    }, [user.attributeIdList, i18n.language]);


    return (
        <section
            className="flex flex-col gap-4 w-full items-center">
            {
                loading ? (
                    <PersonalizationSliderSkeleton />
                    ): (
                    <PersonalizationSlider
                        label={t("stepTwo.interests")}
                        id={"interests"}
                        allOptions={interests}
                        setUser={setUser}
                        user={user}
                    />
                )
            }
        </section>
    )
}

export default PersonalizationStepTwo;