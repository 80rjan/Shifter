import {useAuthContext} from "../context/AuthContext.tsx";
import React, {useEffect, useState} from "react";
import {fetchExpertFreeTimeSlotsApi, scheduleMeetingApi} from "../api/meetingApi.ts";
import type {UserMeetingInfoRequest} from "../models/UserMeetingInfoRequest.tsx";
import { useTranslation } from "react-i18next";
import {useUserContext} from "../context/UserContext.tsx";

import Arrow from "../../public/Shifter-Arrow-White.png";
import {TextInputContactForm} from "../components/inputs/TextInputContactForm.tsx";

function FreeConsultation() {
    const { t } = useTranslation("freeConsultation");
    const { accessToken, authChecked} = useAuthContext();
    const { user, setUser} = useUserContext();
    const [loadingDateTime, setLoadingDateTime] = useState(true);
    const [loadingSubmitForm, setLoadingSubmitForm] = useState(false);
    const [error, setError] = useState<string>("");
    const [selectedDate, setSelectedDate] = useState<string>("");
    const [selectedTime, setSelectedTime] = useState<string>("");
    const [freeSlots, setFreeSlots] = useState<Record<string, string[]>>({"": [""]});
    const [userMeetingInfo, setUserMeetingInfo] = useState<UserMeetingInfoRequest>({
        basicInfo: "",
        aboutCompany: "",
        challenges: "",
        otherInfo: ""
    });
    const [meetingScheduled, setMeetingScheduled] = useState<boolean>(false);

    const handleScheduleMeeting = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (!selectedDate || !selectedTime) {
            setError(t("errors.selectDateTime"));
            return;
        }

        if (!userMeetingInfo.basicInfo || !userMeetingInfo.aboutCompany || !userMeetingInfo.challenges) {
            setError(t("errors.completeAllInputs"))
            return;
        }

        setLoadingSubmitForm(true);
        scheduleMeetingApi(accessToken || "", selectedTime, selectedDate, userMeetingInfo)
            .then(() => {
                setMeetingScheduled(true);
                setError("");

                if (user)
                    setUser({...user, usedFreeConsultation: true})
            })
            .catch(error => {
                console.error("Error scheduling meeting:", error);
                if (error.response?.data?.message === "User has already used free consultation")
                    setError(t("errors.alreadyUsed"));
                else
                    setError(t("errors.failedSchedule"));
            })
            .finally(() => setLoadingSubmitForm(false));
    }

    useEffect(() => {
        if (!authChecked || !accessToken) return;

        setLoadingDateTime(true);
        fetchExpertFreeTimeSlotsApi(accessToken)
            .then(data => setFreeSlots(data))
            .catch(error => console.error("Error fetching time slots:", error))
            .finally(() => setLoadingDateTime(false));
    }, [authChecked, accessToken]);

    // Assert type for TypeScript
    const steps = t("nextSteps.steps", { returnObjects: true }) as { title: string; description: string }[];

    return (
        <main className="flex flex-col gap-between bg-gradient-to-b from-shifter via-shifter/20 via-40% to-main">
            {/*Hero*/}
            <section
                className="flex flex-col items-center gap-2 w-full  pt-nav-top px-horizontal text-white-text text-center max-w-screen-2xl mx-auto
                    sm:gap-3
                    md:gap-4
                    ">
                <h1 className="text-3xl font-bold
                    sm:text-4xl
                    md:text-5xl
                    lg:text-5xl
                    xl:text-6xl
                    2xl:text-7xl">{t("hero.title")}</h1>
                <p className="text-base font-light
                    sm:text-lg
                    md:text-xl
                    lg:text-xl
                    xl:text-2xl
                    2xl:text-3xl">{t("hero.description")}</p>
            </section>

            {/*Contact Form*/}
            <section className="flex items-center justify-center w-full px-horizontal pb-vertical">
                <div
                    className="relative flex flex-col gap-8 rounded-lg bg-white p-4 shadow-md shadow-black/10 w-full max-w-screen-2xl mx-auto
                        sm:gap-10 sm:p-6
                        md:gap-12
                        lg:grid lg:grid-cols-3 lg:gap-x-12
                        xl:gap-x-20">

                    {/*Contact Info*/}
                    <div
                        className="border-1 border-white/40 relative overflow-clip flex flex-col gap-5 py-8 px-6 rounded-lg bg-shifter text-white
                            sm:gap-6 sm:py-10 sm:px-8
                            lg:col-span-1 lg:gap-8">
                        <h2 className="text-left text-xl font-semibold
                            sm:text-2xl">{t("nextSteps.title")}</h2>
                        <ol className="flex flex-col">
                            {steps.map((step, idx) => (
                                <Step
                                    key={idx}
                                    title={step.title}
                                    description={step.description}
                                    isLast={idx === steps.length - 1}
                                />
                            ))}
                        </ol>

                        <div className="absolute -bottom-20 right-8 flex flex-col opacity-40 rotate-40
                            sm:-bottom-26 sm:right-12
                            lg:-bottom-26 lg:right-12">
                            <img src={Arrow} className="w-20 h-auto sm:w-28" alt="Shifter Arrow"/>
                            <img src={Arrow} className="w-20 h-auto sm:w-28 rotate-180" alt="Shifter Arrow"/>
                        </div>
                    </div>


                    {/*Form*/}
                    <div className="flex flex-col gap-6 flex-1
                        sm:gap-7
                        lg:col-span-2 lg:py-6">
                        <div className="flex flex-col gap-3
                            sm:grid sm:grid-cols-2 sm:gap-x-6 sm:gap-y-2 sm:items-center">
                            <p className="text-black/40 text-xs col-span-2 mb-1
                                md:text-sm
                                2xl:text-base">{t("form.profileHint")}</p>
                            <p className="font-light text-black-text/60 text-sm leading-relaxed
                                sm:text-md sm:whitespace-nowrap
                                md:text-base
                                2xl:text-lg">
                                {t("form.name")}: <span className="text-black-text font-normal">{user?.name}</span>
                            </p>
                            <p className="font-light text-black-text/60 text-sm leading-relaxed
                                sm:text-md sm:whitespace-nowrap
                                md:text-base
                                2xl:text-lg">
                                {t("form.email")}: <span className="text-black-text font-normal">{user?.email}</span>
                            </p>
                        </div>

                        <hr className="border-t-1 border-black/20 rounded-full my-2"/>

                        <form onSubmit={handleScheduleMeeting} className="flex flex-col gap-6 items-start
                            sm:gap-7">
                            <div className="flex flex-col gap-7 items-center w-full
                                sm:gap-8">
                                {["basicInfo", "aboutCompany","challenges","otherInfo"].map(field => (
                                    <TextInputContactForm
                                        key={field}
                                        label={t(`form.fields.${field}.label`)}
                                        name={field}
                                        rows={2}
                                        placeholder={t(`form.fields.${field}.placeholder`)}
                                        onChange={(e) => setUserMeetingInfo({...userMeetingInfo, [field]: e.target.value})}
                                    />
                                ))}

                                <hr className="border-t-1 border-black/20 rounded-full w-full"/>
                                <div className="flex flex-col gap-3 w-full
                                    sm:flex-row sm:justify-between sm:items-center sm:gap-4">
                                    {loadingDateTime ? (
                                        <>
                                            <div className="bg-gray-300 animate-pulse py-2 px-8 rounded-sm h-10 w-full
                                                sm:w-50"></div>
                                            <div className="bg-gray-300 animate-pulse py-2 px-8 rounded-sm h-10 w-full
                                                sm:w-50"></div>
                                        </>
                                    ) : (
                                        <>
                                            <SelectInput
                                                value={selectedDate}
                                                onChange={(e) => setSelectedDate(e.target.value)}
                                                firstOption={t("form.selectDate")}
                                                options={Object.keys(freeSlots)}
                                            />
                                            <SelectInput
                                                value={selectedTime}
                                                onChange={(e) => setSelectedTime(e.target.value)}
                                                firstOption={t("form.selectTime")}
                                                options={freeSlots[selectedDate]}
                                                isDisabled={selectedDate.length === 0}
                                            />
                                        </>
                                    )}
                                </div>

                                {error && <p className="text-red-500 text-sm font-medium text-center mt-2
                                    sm:text-md">{error}</p>}
                            </div>

                            {!meetingScheduled ? (
                                <div className="text-center w-full mt-4">
                                    <h2 className="text-lg font-bold text-shifter mb-3
                                        sm:text-xl
                                        md:text-2xl">{t("confirmation.title")}</h2>
                                    <p className="text-sm font-medium text-black/80 max-w-xl mx-auto leading-relaxed
                                        sm:text-base
                                        md:text-lg" dangerouslySetInnerHTML={{__html: t("confirmation.description")}}/>
                                </div>
                            ) : (
                                <div className="flex flex-col items-center gap-4 w-full mt-6
                                    sm:flex-row sm:gap-6">
                                    <button
                                        className="hover:shadow-shifter/60 transition-all duration-200 ease-in-out disabled:cursor-not-allowed disabled:opacity-60 shadow-md shadow-shifter/40 bg-shifter text-white rounded-md cursor-pointer w-full
                                        text-sm font-medium py-2.5
                                        md:text-base
                                        2xl:text-lg 2xl:py-3"
                                        disabled={loadingSubmitForm}
                                        type="submit"
                                    >
                                        {loadingSubmitForm ? t("form.booking") : t("form.book")}
                                    </button>
                                    {loadingSubmitForm && <div className="loader"/>}
                                </div>
                            )}
                        </form>
                    </div>
                </div>
            </section>
        </main>
    )
}

function SelectInput({value, onChange, firstOption, options, isDisabled}: {value:string,onChange:(e:React.ChangeEvent<HTMLSelectElement>)=>void,firstOption:string,options:string[],isDisabled?:boolean}) {
    return (
        <select required className={`disabled:opacity-20 disabled:cursor-not-allowed bg-dark-blue/5 border-1 border-black/10 py-1 px-4 rounded-sm ${value ? "text-black-text" : "text-black-text/60"} 
                        resize-none overflow-hidden min-h-fit cursor-pointer w-full text-sm
                        sm:w-auto 
                        md:text-base`} disabled={isDisabled} value={value} onChange={onChange}>
            <option value="" className="text-black/40">{firstOption}</option>
            {options?.map(option => <option key={option} value={option} className="text-black-text">{option}</option>)}
        </select>
    )
}

function Step({title, description, isLast}: {title:string,description:string,isLast?:boolean}) {
    return (
        <li className={`${isLast ? 'border-transparent' : ''} relative pl-4 border-l-4 border-white/10
            sm:pl-6`}>
            <div style={{transform: 'translateX(calc(-0.5rem - 2px))'}} className="absolute left-0 top-0 h-3 aspect-square bg-white rounded-full
                sm:h-[1rem]"/>
            <div className="pb-6 text-left
                sm:pb-8">
                <h3 className="text-sm font-semibold text-white
                    sm:text-md
                    md:text-lg
                    2xl:text-xl">{title}</h3>
                <p className="text-sm text-white/60 font-light
                    sm:text-md
                    md:text-lg
                    2xl:text-xl">{description}</p>
            </div>
        </li>
    )
}

export default FreeConsultation;
