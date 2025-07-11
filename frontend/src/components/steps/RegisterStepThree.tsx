import React, {useEffect} from "react";
import type {UserRegister} from "../../types/UserRegister.tsx";
import RegisterSlider from "../inputs/RegisterSlider.tsx";
import {fetchCoursesTopicsApi} from "../../api/courseApi.ts";

function RegisterStepThree({setUser, user, setError}: {
    setUser: React.Dispatch<React.SetStateAction<UserRegister>>,
    user: UserRegister,
    setError: React.Dispatch<React.SetStateAction<string>>,
}) {
    const [interests, setInterests] = React.useState<string[]>([]);

    useEffect(() => {
        const fetchCourseTopics = async () => {
            try {
                const topicsData = await fetchCoursesTopicsApi();
                setInterests(topicsData);
            } catch (err) {
                console.error("Failed to fetch course topics (user interests)", err);
            }
        };

        fetchCourseTopics();
    }, []);

    useEffect(() => {
        if (user.interests.length === 0) {
            setError("Help us understand you better — pick at least one preference");
        } else {
            setError("");
        }
    }, [user.interests]);


    return (
        <section
            className="flex flex-col gap-4 w-full items-center">
            {
                interests.length > 0 &&
                <RegisterSlider
                    label={"Pick Your Preferences"}
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

export default RegisterStepThree;