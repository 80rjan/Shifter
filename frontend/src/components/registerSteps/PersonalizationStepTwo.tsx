import React, {useEffect} from "react";
import type {UserPersonalization} from "../../models/javaObjects/UserPersonalization.tsx";
import PersonalizationSlider from "../inputs/PersonalizationSlider.tsx";
import {fetchCoursesTopicsApi} from "../../api/courseApi.ts";

function PersonalizationStepTwo({setUser, user, setError}: {
    setUser: React.Dispatch<React.SetStateAction<UserPersonalization>>,
    user: UserPersonalization,
    setError: React.Dispatch<React.SetStateAction<string>>,
}) {
    const [interests, setInterests] = React.useState<string[]>([]);

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
            setError("Help us understand you better — select at least one topic you like");
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
                    label={"Select Topics You Like"}
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