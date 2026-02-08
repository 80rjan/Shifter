import {useAuthContext} from "../context/AuthContext.tsx";
import React, {useState} from "react";
import {updateUserApi} from "../api/userApi.ts";
import {useTranslation} from "react-i18next";
import {useUserContext} from "../context/UserContext.tsx";

function ProfileMyProfile() {
    const [loading, setLoading] = useState(false);
    const [success, setSuccess] = useState(false);
    const {accessToken} = useAuthContext();
    const {user, setUser} = useUserContext();
    const {t} = useTranslation("profile");

    const [formData, setFormData] = useState({
        name: user?.name || "",
        workPosition: user?.workPosition || "",
        companySize: user?.companySize || ""
    });

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        setLoading(true);
        updateUserApi(formData, accessToken || "")
            .then(() => {
                setSuccess(true);
                setUser(prev => {
                    if (!prev) return prev;
                    return {
                        ...prev,
                        name: formData.name,
                        workPosition: formData.workPosition,
                        companySize: formData.companySize
                    };
                });
            })
            .catch(error => {
                console.error("Error updating user:", error);
            })
            .finally(() => {
                setLoading(false);
            });
    };

    const selectOptions = [
        {value: "FREELANCE", label: t("companySizeOptions.freelance")},
        {value: "MICRO", label: t("companySizeOptions.micro")},
        {value: "SMALL", label: t("companySizeOptions.small")},
        {value: "MEDIUM", label: t("companySizeOptions.medium")},
        {value: "MID_MARKET", label: t("companySizeOptions.midMarket")},
        {value: "ENTERPRISE", label: t("companySizeOptions.enterprise")},
        {value: "OTHER", label: t("companySizeOptions.other")},
    ];

    return (
        <section className="flex flex-col gap-3 items-start
            sm:gap-4">
            <h2 className="text-2xl font-semibold
                sm:text-2xl
                md:text-3xl">{t("myProfile.title")}</h2>
            <form
                onSubmit={handleSubmit}
                className="shadow-md flex flex-col gap-3 px-4 py-4 border-1 border-dark-blue/10 bg-white rounded-lg w-full
                    sm:gap-4 sm:px-8 sm:py-5
                    md:px-12 md:py-6
                    lg:px-24"
            >
                <Input
                    name={"name"}
                    value={formData.name}
                    func={setFormData}
                    placeholder={t("myProfile.fields.name")}
                    label={t("myProfile.fieldLabels.name")}
                />
                <Input
                    name={"workPosition"}
                    value={formData.workPosition}
                    func={setFormData}
                    placeholder={t("myProfile.fields.workPosition")}
                    label={t("myProfile.fieldLabels.workPosition")}
                />
                <label className="w-full flex flex-col items-start gap-1">
                    <span className="text-black/40 font-medium text-sm peer-focused:text-shifter
                        sm:text-md">
                        {t("myProfile.fieldLabels.companySize")}
                    </span>
                    <select
                        className="hover:border-1 hover:border-shifter focus:outline-none focus:border-2 focus:border-shifter
                        border-1 border-black/40 rounded-sm py-1.5 px-3 text-base text-black w-full cursor-pointer
                            sm:py-2 sm:px-4 sm:text-lg"
                        name={"companySize"}
                        value={formData.companySize}
                        onChange={e =>
                            setFormData(prev => ({...prev, companySize: e.target.value}))
                        }
                    >
                        <option value="">{t("myProfile.fields.companySize")}</option>
                        {selectOptions.map(option => (
                            <option value={option.value} key={option.value}>
                                {option.label}
                            </option>
                        ))}
                    </select>
                </label>

                <div className="flex flex-col gap-3 items-center mt-2
                    sm:flex-row sm:gap-4">
                    <button
                        className="hover:shadow-shifter/40 transition-all duration-300 ease-in-out cursor-pointer
                        shadow-md shadow-shifter/20
                        bg-shifter px-8 py-2 w-full text-white rounded-sm font-semibold border-2 border-white/40 text-sm
                            sm:px-12 sm:w-fit sm:text-base"
                        type="submit"
                    >
                        {loading ? t("myProfile.saving") : t("myProfile.saveChanges")}
                    </button>
                    {loading && <div className="h-full loader"></div>}
                    {
                        success && <p className="text-shifter font-bold text-sm
                            sm:text-base">{t("successfulUpdate")}</p>
                    }
                </div>
            </form>
        </section>
    );
}

function Input({
                   name,
                   value,
                   func,
                   placeholder,
                   label
               }: {
    name: string;
    value: string;
    func: React.Dispatch<
        React.SetStateAction<{
            name: string;
            workPosition: string;
            companySize: string;
        }>
    >;
    placeholder: string;
    label: string;
}) {
    return (
        <label className="w-full flex flex-col items-start gap-1">
            <span className="text-black/40 font-medium text-sm peer-focused:text-shifter
                sm:text-md">{label}</span>
            <input
                className="hover:border-1 hover:border-shifter focus:outline-none focus:border-2 focus:border-shifter
                border-1 border-black/40 rounded-sm py-1.5 px-3 text-base text-black w-full
                    sm:py-2 sm:px-4 sm:text-lg"
                value={value}
                onChange={e => func(prev => ({...prev, [name]: e.target.value}))}
                name={name}
                placeholder={placeholder}
            />
        </label>
    );
}

export default ProfileMyProfile;
