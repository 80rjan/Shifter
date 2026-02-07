import {useAuthContext} from "../context/AuthContext.tsx";
import React, {useEffect, useState} from "react";
import {fetchExpertFreeTimeSlotsApi, scheduleMeetingApi} from "../api/meetingApi.ts";
import type {UserMeetingInfoRequest} from "../models/UserMeetingInfoRequest.tsx";
import { useTranslation } from "react-i18next";
import {useUserContext} from "../context/UserContext.tsx";

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
        <main className="bg-gradient-to-b from-shifter via-shifter/20 via-40% to-main">
            {/*Hero*/}
            <section className="flex flex-col items-center gap-4 w-full pb-60 pt-nav-top px-horizontal text-white-text">
                <h1 className="text-5xl font-bold">{t("hero.title")}</h1>
                <p className="text-xl font-light">{t("hero.description")}</p>
            </section>

            {/*Contact Form*/}
            <section className="flex items-center justify-center w-full px-horizontal">
                <div className="relative -top-40 grid grid-cols-3 gap-x-20 rounded-lg bg-white p-4 shadow-md shadow-black/10">

                    {/*Contact Info*/}
                    {/*Contact Info*/}
                    <div className="border-1 border-white/40 relative overflow-clip col-span-1 flex flex-col gap-8 py-8 px-8 rounded-lg bg-shifter text-white">
                        <h2 className="text-left text-2xl font-semibold">{t("nextSteps.title")}</h2>
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
                    </div>


                    {/*Form*/}
                    <div className="flex flex-col gap-4 col-span-2">
                        <div className="grid grid-cols-2 gap-4 items-center">
                            <p className="text-black/40 text-xs col-span-2">{t("form.profileHint")}</p>
                            <p className="font-light text-black-text/60 text-md whitespace-nowrap">{t("form.name")}: <span className="text-black-text font-normal">{user?.name}</span></p>
                            <p className="font-light text-black-text/60 text-md whitespace-nowrap">{t("form.email")}: <span className="text-black-text font-normal">{user?.email}</span></p>
                        </div>

                        <hr className="border-t-1 border-black/20 rounded-full"/>

                        <form onSubmit={handleScheduleMeeting} className="flex flex-col gap-12 items-start">
                            <div className="flex flex-col gap-4 items-center w-full">
                                {["basicInfo", "aboutCompany","challenges","otherInfo"].map(field => (
                                    <TextInput
                                        key={field}
                                        label={t(`form.fields.${field}.label`)}
                                        name={field}
                                        placeholder={t(`form.fields.${field}.placeholder`)}
                                        onChange={(e) => setUserMeetingInfo({...userMeetingInfo, [field]: e.target.value})}
                                    />
                                ))}

                                <hr className="border-t-1 border-black/20 rounded-full w-full"/>
                                <div className="flex justify-between items-center w-full">
                                    {loadingDateTime ? (
                                        <>
                                            <div className="bg-gray-300 animate-pulse py-2 px-8 rounded-sm h-10 w-50"></div>
                                            <div className="bg-gray-300 animate-pulse py-2 px-8 rounded-sm h-10 w-50"></div>
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

                                {error && <p className="text-red-500 text-md font-medium text-center">{error}</p>}
                            </div>

                            {meetingScheduled ? (
                                <div className="text-center w-full">
                                    <h2 className="text-xl font-bold text-shifter mb-2">{t("confirmation.title")}</h2>
                                    <p className="text-md font-medium text-black/80 max-w-xl mx-auto" dangerouslySetInnerHTML={{__html: t("confirmation.description")}}/>
                                </div>
                            ) : (
                                <div className="flex items-center gap-6">
                                    <button
                                        className="hover:shadow-shifter/40 transition-all duration-300 ease-in-out disabled:cursor-not-allowed disabled:opacity-60 shadow-md shadow-shifter/20 bg-shifter text-white py-2 px-6 rounded-md cursor-pointer"
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

function TextInput({label, name, placeholder, onChange}: {
    label:string,
    name:string,
    placeholder:string,
    onChange:(e:React.ChangeEvent<HTMLTextAreaElement>)=>void,
}) {
    return (
        <label className="w-full flex flex-col items-start gap-2 text-black-text">
            <span className="text-black/40 font-semibold text-md peer-focused:text-shifter">
                {label}
            </span>
            <textarea rows={2} name={name} onChange={onChange} placeholder={placeholder} className="peer w-full bg-dark-blue/5 border-1 border-black/10 py-1 px-2 rounded-sm resize-none min-h-fit custom-scrollbar focus:outline-none focus:border-shifter/40 focus:border-2"/>
        </label>
    )
}

function SelectInput({value, onChange, firstOption, options, isDisabled}: {value:string,onChange:(e:React.ChangeEvent<HTMLSelectElement>)=>void,firstOption:string,options:string[],isDisabled?:boolean}) {
    return (
        <select required className={`disabled:opacity-20 disabled:cursor-not-allowed bg-dark-blue/5 border-1 border-black/10 py-1 px-4 rounded-sm ${value ? "text-black-text" : "text-black/40"} resize-none overflow-hidden min-h-fit cursor-pointer`} disabled={isDisabled} value={value} onChange={onChange}>
            <option value="" className="text-black/40">{firstOption}</option>
            {options?.map(option => <option key={option} value={option} className="text-black-text">{option}</option>)}
        </select>
    )
}

function Step({title, description, isLast}: {title:string,description:string,isLast?:boolean}) {
    return (
        <li className={`${isLast ? 'border-transparent' : ''} relative pl-6 border-l-4 border-white/10`}>
            <div style={{transform: 'translateX(calc(-0.5rem - 2px))'}} className="absolute left-0 top-0 h-[1rem] aspect-square bg-white rounded-full"/>
            <div className="pb-8 text-left">
                <h3 className="text-md font-semibold text-white">{title}</h3>
                <p className="text-sm text-white/60 font-light">{description}</p>
            </div>
        </li>
    )
}

export default FreeConsultation;
