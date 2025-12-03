import React, {useEffect} from "react";
import type {UserPersonalization} from "../../models/javaObjects/UserPersonalization.tsx";
import PersonalizationInput from "../inputs/PersonalizationInput.tsx";
import PersonalizationSelect from "../inputs/PersonalizationSelect.tsx";
import {useTranslation} from "react-i18next";
import {companySizeOptions} from "../../models/types/CompanySize.tsx";

function PersonalizeStepOne({setUser, user, setError}: {
    setUser: React.Dispatch<React.SetStateAction<UserPersonalization>>,
    user: UserPersonalization,
    setError: React.Dispatch<React.SetStateAction<string>>,
}) {
    const { t } = useTranslation("personalize");
    const { i18n } = useTranslation();

    useEffect(() => {
        if (!user.name || !user.workPosition || !user.companySize) {
            setError(t("stepOne.errorIncomplete"));
        } else {
            setError("");
        }
    }, [user.name, user.workPosition, user.companySize, i18n.language]);

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
                optionsShow={t("stepOne.companySizeOptions", {returnObjects: true}) as string[]}
                options={companySizeOptions}
                setUser={setUser}
                user={user}
            />
        </section>
    );
}

export default PersonalizeStepOne;