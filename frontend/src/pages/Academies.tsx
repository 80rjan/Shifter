import {Mail, MapPin} from "lucide-react"
import React from "react";
import {useAuthContext} from "../context/AuthContext.tsx";
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import Arrow from "../../public/Shifter-Arrow-White.png"
import {sendEmailApi} from "../api/contactApi.ts";
import { useTranslation } from "react-i18next";
import {useUserContext} from "../context/UserContext.tsx";
import type {AcademiesMessageState} from "../models/state/EmailMessageState.tsx";
import {TextInputContactForm} from "../components/inputs/TextInputContactForm.tsx";
import {PillsInputContactForm} from "../components/inputs/PillsInputContactForm.tsx";
import {RadioInputContactForm} from "../components/inputs/RadioInputContactForm.tsx";

function Academies() {
    const {t} = useTranslation("academies");
    const {accessToken} = useAuthContext();
    const { user } = useUserContext();
    const [fields, setFields] = React.useState<AcademiesMessageState>({
        business: "",
        departments: [],
        areaOfInterest: [],
        participants: "",
        urgency: ""
    });
    const [loading, setLoading] = React.useState(false);
    const [error, setError] = React.useState("");
    const [isMessageSent, setIsMessageSent] = React.useState(false);

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        const message = `
        Business size: ${user?.companySize || "Not provided"}
        
        Business name: ${fields.business}
        
        Departments/Teams that should participate in the academy training: ${fields.departments.join(", ")}
        
        Training areas of interest: ${fields.areaOfInterest.join(", ")}
        
        Participants: ${fields.participants}
        
        Urgency: ${fields.urgency}
        `;

        const subject = `Academy Inquiry from ${user?.name} (${user?.email})`;

        setLoading(true);
        sendEmailApi(accessToken || "", subject, message)
            .then(() => {
                setIsMessageSent(true);
                setError("")
            })
            .catch((err) => {
                setError(t("emailError"));
                console.error("Error sending email for academy inquiry:", err);
            })
            .finally(() => setLoading(false));
    }

    return (
        <main className="bg-gradient-to-b from-shifter via-shifter/20 via-40% to-main">
            {/*Hero*/}
            <section className="flex flex-col items-center gap-4 w-full pb-60 pt-top-nav-lg px-horizontal-lg
                text-white-text">
                <h1 className="text-5xl font-bold" dangerouslySetInnerHTML={{__html: t("heroTitle")}}/>
                <p className="text-xl font-light" dangerouslySetInnerHTML={{__html: t("heroDescription")}}/>
            </section>

            {/*Contact Form*/}
            <section className=" flex items-center justify-center w-full px-horizontal-lg">
                <div className="relative -top-40 grid grid-cols-3 gap-x-20 rounded-lg bg-white p-4
                    shadow-md shadow-black/10">

                    {/*Contact Info*/}
                    <div className="border-1 border-white/40
                    relative overflow-clip col-span-1 flex flex-col gap-8 py-8 px-8 rounded-lg bg-shifter text-white">
                        <div className="flex flex-col gap-2 items-start text-left">
                            <h2 className="text-2xl font-semibold whitespace-nowrap">{t("contactInfoTitle")}</h2>
                            <p className="text-white/80 font-light text-sm">{t("contactInfoDescription")}</p>
                        </div>
                        <div className="flex gap-4 items-center">
                            <Mail size={24} color="var(--color-white)"/>
                            {t("contactEmail")}
                        </div>
                        <div className="flex gap-4 items-center">
                            <MapPin size={24} color="var(--color-white)"/>
                            {t("contactLocation")}
                        </div>

                        <div className="absolute -bottom-26 right-12 flex flex-col opacity-40 rotate-40">
                            <img src={Arrow} className="w-28 h-auto" alt="Shifter Arrow"/>
                            <img src={Arrow} className="w-28 h-auto rotate-180" alt="Shifter Arrow"/>
                        </div>
                    </div>

                    {/*Form*/}
                    <div className="flex flex-col gap-4 col-span-2">
                        <div className="grid grid-cols-2 gap-4 items-center">
                            <p className="text-black/40 text-xs col-span-2">{t("autoFilledNote")}</p>
                            <p className="font-light text-black-text/60 text-md whitespace-nowrap ">
                                {t("name")}: <span className="text-black-text font-normal">{user?.name}</span>
                            </p>
                            <p className="font-light text-black-text/60 text-md whitespace-nowrap ">
                                {t("email")}: <span className="text-black-text font-normal">{user?.email}</span>
                            </p>
                        </div>

                        <hr className="border-t-1 border-black/20 rounded-full"/>

                        <form
                            onSubmit={handleSubmit}
                            className="flex flex-col gap-4 items-start">
                            <div className="flex flex-col gap-4 items-center w-full">
                                <TextInputContactForm
                                    label={t("businessLabel")}
                                    name="business"
                                    placeholder={t("businessPlaceholder")}
                                    rows={1}
                                    onChange={(e) => setFields({...fields, business: e.target.value})}
                                />
                                <PillsInputContactForm
                                    label={t("departmentsLabel")}
                                    onChange={(values) => setFields({...fields, departments: values})}
                                    options={t("departmentOptions", {returnObjects: true}) as string[]}
                                />
                                <PillsInputContactForm
                                    label={t("areaOfInterestLabel")}
                                    onChange={(values) => setFields({...fields, areaOfInterest: values})}
                                    options={t("areaOfInterestOptions", {returnObjects: true}) as string[]}
                                />
                                <RadioInputContactForm
                                    label={t("numberOfParticipantsLabel")}
                                    name={"areaOfInterest"}
                                    onChange={(value) => setFields({...fields, participants: value})}
                                    options={t("numberOfParticipantsOptions", {returnObjects: true}) as string[]}
                                />
                                <RadioInputContactForm
                                    label={t("urgencyLabel")}
                                    name={"urgency"}
                                    onChange={(value) => setFields({...fields, urgency: value})}
                                    options={t("urgencyOptions", {returnObjects: true}) as string[]}
                                />
                                {
                                    error && (
                                        <p className="text-red-500 text-md font-medium text-center">
                                            {error}
                                        </p>
                                    )
                                }
                            </div>
                            {
                                isMessageSent ? (
                                    <div className="text-center w-full">
                                        <h2 className="text-xl font-bold text-shifter mb-2">{t("messageSentTitle")}</h2>
                                        <p className="text-md font-medium text-black/80 max-w-xl mx-auto" dangerouslySetInnerHTML={{__html: t("messageSentDescription")}}/>
                                    </div>
                                ) : (
                                    <div className="flex items-center gap-6">
                                        <button
                                            className="hover:shadow-shifter/60 transition-all duration-200 ease-in-out
                                    disabled:cursor-not-allowed disabled:opacity-60
                                    shadow-md shadow-shifter/40 bg-shifter text-white py-2 px-6 rounded-md cursor-pointer"
                                            disabled={loading}
                                            type="submit">
                                            {loading ? t("sending") : t("sendMessage")}
                                        </button>
                                        {
                                            loading && <div className="loader"/>
                                        }
                                    </div>
                                )}
                        </form>
                    </div>
                </div>
            </section>
        </main>
    )
}

export default Academies;
