import React, {useEffect} from "react";
import type {UserPersonalization} from "../../models/javaObjects/UserPersonalization.tsx";
import PersonalizationInput from "../inputs/PersonalizationInput.tsx";
import PersonalizationSelect from "../inputs/PersonalizationSelect.tsx";

function PersonalizeStepOne({setUser, user, setError}: {
    setUser: React.Dispatch<React.SetStateAction<UserPersonalization>>,
    user: UserPersonalization,
    setError: React.Dispatch<React.SetStateAction<string>>,
}) {

    useEffect(() => {
        if (!user.name || !user.workPosition || !user.companySize) {
            setError("Please ensure all inputs are completed.");
        } else {
            setError("");
        }
    }, [user.name, user.workPosition, user.companySize]);

    return (
        <section
            className="flex flex-col gap-4 w-full items-center">
            <PersonalizationInput
                placeholder={"John Doe"}
                label={"Full Name"}
                name={"name"}
                type={"text"}
                id={"full-name"}
                setUser={setUser}
                user={user}
            />
            <PersonalizationInput
                placeholder={"Your Position"}
                label={"Work Position"}
                name={"workPosition"}
                type={"text"}
                id={"work-position"}
                setUser={setUser}
                user={user}
            />
            <PersonalizationSelect
                label={"Company Size"}
                name={"companySize"}
                id={"company-size"}
                options={["Freelance", "Micro", "Small", "Medium", "Mid Market", "Enterprise", "Other"]}
                setUser={setUser}
                user={user}
            />
        </section>
    );
}

export default PersonalizeStepOne;