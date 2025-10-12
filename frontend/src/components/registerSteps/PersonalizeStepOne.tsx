import React, {useEffect} from "react";
import type {UserPersonalization} from "../../models/javaObjects/UserPersonalization.tsx";
import PersonalizationInput from "../inputs/PersonalizationInput.tsx";
import PersonalizationSelect from "../inputs/PersonalizationSelect.tsx";
import {useTranslation} from "react-i18next";

function PersonalizeStepOne({setUser, user, setError}: {
    setUser: React.Dispatch<React.SetStateAction<UserPersonalization>>,
    user: UserPersonalization,
    setError: React.Dispatch<React.SetStateAction<string>>,
}) {
    const { t } = useTranslation("personalize");

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
                placeholder={t("stepOne.fullNamePlaceholder")}
                label={t("stepOne.fullName")}
                name={"name"}
                type={"text"}
                id={"full-name"}
                setUser={setUser}
                user={user}
            />
            <PersonalizationInput
                placeholder={t("stepOne.workPositionPlaceholder")}
                label={t("stepOne.workPosition")}
                name={"workPosition"}
                type={"text"}
                id={"work-position"}
                setUser={setUser}
                user={user}
            />
            <PersonalizationSelect
                label={t("stepOne.companySize")}
                name={"companySize"}
                id={"company-size"}
                options={t("stepOne.companySizeOptions") as unknown as string[]}
                setUser={setUser}
                user={user}
            />
        </section>
    );
}

export default PersonalizeStepOne;