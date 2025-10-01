import {useAuthContext} from "../context/AuthContext.tsx";
import React, {useEffect, useState} from "react";
import {fetchExpertFreeTimeSlotsApi, scheduleMeetingApi} from "../api/meetingApi.ts";
import type {UserMeetingInfoRequest} from "../models/UserMeetingInfoRequest.tsx";
import ShifterArrow from "../../public/Shifter-Arrow-White.png"

function FreeConsultation() {
    const {user, setUser, accessToken, authChecked} = useAuthContext();
    const [loadingDateTime, setLoadingDateTime] = useState(true);
    const [loadingSubmitForm, setLoadingSubmitForm] = useState(false);
    const [error, setError] = useState<string>("");
    const [selectedDate, setSelectedDate] = useState<string>("");
    const [selectedTime, setSelectedTime] = useState<string>("");
    const [freeSlots, setFreeSlots] = useState<Record<string, string[]>>({"": [""]});
    const [userMeetingInfo, setUserMeetingInfo] = useState<UserMeetingInfoRequest>({
        aboutCompany: "",
        challenges: "",
        expectations: "",
        otherInfo: ""
    });
    const [meetingScheduled, setMeetingScheduled] = useState<boolean>(false);

    const handleScheduleMeeting = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (!selectedDate || !selectedTime) {
            setError("Please select both date and time.");
            return;
        }

        setLoadingSubmitForm(true);
        scheduleMeetingApi(accessToken || "", selectedTime, selectedDate, userMeetingInfo)
            .then(() => {
                setMeetingScheduled(true);
                setError("");

                if (user)
                    setUser({...user, hasUsedFreeConsultation: true})
            })
            .catch(error => {
                console.error("Error scheduling meeting:", error);
                setError("Failed to schedule the meeting. Please try again later or contact support.");
            })
            .finally(() => setLoadingSubmitForm(false));
    }

    useEffect(() => {
        if (!authChecked || !accessToken)
            return;

        setLoadingDateTime(true);
        fetchExpertFreeTimeSlotsApi(accessToken)
            .then(data => {
                console.log("Available time slots:", data);
                setFreeSlots(data)
            })
            .catch(error => {
                console.error("Error fetching time slots:", error);
            })
            .finally(() => setLoadingDateTime(false));
    }, []);

    return (
        <main className="">
            {/*HERO*/}
            <section
                style={{paddingTop: 'calc(var(--spacing-top-nav-lg) + 4rem)'}}
                className="relative bg-dark-blue text-white w-full px-horizontal-lg py-vertical-lg pt-top-nav-lg text-left overflow-x-clip">
                <div className="flex flex-col gap-4 w-1/2">
                    <h1 className="text-5xl font-bold">Book Your Free Expert Session</h1>
                    <p className="text-xl font-light ">
                        Talk to an expert about your business goals and challenges.
                        Get a personalized program recommendation tailored to your needs.
                    </p>
                </div>

                <img src={ShifterArrow} className="absolute right-20 -bottom-20 h-120 rotate-45 opacity-5"/>
            </section>

            <section className="bg-dark-blue/5 flex gap-20 py-vertical-lg px-horizontal-lg text-left">
                {/*STEPS*/}
                <div className="flex flex-col gap-12 w-1/3">
                    <h2 className="text-dark-blue font-bold text-5xl">Next Steps After Submission?</h2>

                    <div>
                        <ol className="flex flex-col">
                            {/*<Step*/}
                            {/*    title={"Submit Your Request"}*/}
                            {/*    description={"Share your business challenges and objectives by completing the form. This helps us understand your needs and prepare a personalized session."}*/}
                            {/*/>*/}
                            <Step
                                title={"Your Expert is Assigned"}
                                description={"We schedule an expert for your session and send you a confirmation email with all the meeting details."}
                            />
                            <Step
                                title={"Expert Prepares for Your Session"}
                                description={"Your expert reviews the information you provided to create valuable insights and a strategy tailored to your situation."}
                            />
                            <Step
                                title={"Attend Your Personalized Session"}
                                description={"Join the session and receive actionable guidance along with a program or mentorship recommendation designed specifically for your goals."}
                                isLast={true}
                            />
                        </ol>
                    </div>
                </div>

                {/*FORM*/}
                <div className="flex flex-col gap-4 bg-white rounded-xl w-2/3 px-horizontal-sm py-vertical-md ">
                    {/*Automatically populated*/}
                    <div className="grid grid-cols-2 gap-y-4">
                        <p className="text-black/40 col-span-2">
                            These values are automatically populated from your profile.
                            If any of them are incorrect, please update them in the Profile page.
                        </p>
                        <p className="font-light text-black/60 text-lg ">Name: <span
                            className="text-black font-medium">{user?.name}</span></p>
                        <p className="font-light text-black/60 text-lg ">Email: <span
                            className="text-black font-medium">{user?.email}</span></p>
                        <p className="font-light text-black/60 text-lg ">Company Type: <span
                            className="text-black font-medium">{user?.companySize.toLowerCase().split("_").map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(" ")}</span>
                        </p>
                        <p className="font-light text-black/60 text-lg ">Work Position: <span
                            className="text-black font-medium">{user?.workPosition}</span></p>
                    </div>

                    <hr className="border-t-2 border-black/20"/>

                    {/*Form*/}
                    <form
                        onSubmit={handleScheduleMeeting}
                        className="flex flex-col gap-6 w-full">
                        <p className="text-black/40 col-span-2">
                            <sup>*</sup> These fields are optional. Filling them out helps us better understand your
                            challenges and objectives, so we can prepare a more personalized session.
                        </p>
                        <TextInput
                            label="About your business"
                            name="intro"
                            placeholder="What you do, industry, customers"
                            onChange={(e) => setUserMeetingInfo({...userMeetingInfo, aboutCompany: e.target.value})}
                        />
                        <TextInput
                            label="Your current challenges"
                            name="challenges"
                            placeholder="E.g. sales, growth, team issues"
                            onChange={(e) => setUserMeetingInfo({...userMeetingInfo, challenges: e.target.value})}
                        />
                        <TextInput
                            label="What you want from the session"
                            name="expectations"
                            placeholder="Advice, strategy, solutions"
                            onChange={(e) => setUserMeetingInfo({...userMeetingInfo, expectations: e.target.value})}
                        />
                        <TextInput
                            label="Anything else"
                            name="additional"
                            placeholder="Extra context or details"
                            onChange={(e) => setUserMeetingInfo({...userMeetingInfo, otherInfo: e.target.value})}
                        />

                        <hr className="border-t-2 border-black/20"/>

                        <div className="flex justify-between items-center">
                            {
                                loadingDateTime ? (
                                    <>
                                        <div className="bg-gray-300 animate-pulse py-2 px-8 rounded-sm h-10 w-50"></div>
                                        <div className="bg-gray-300 animate-pulse py-2 px-8 rounded-sm h-10 w-50"></div>
                                    </>
                                ) : (
                                    <>
                                        <SelectInput
                                            value={selectedDate}
                                            onChange={(e) => setSelectedDate(e.target.value)}
                                            firstOption={"Select a date"}
                                            options={Object.keys(freeSlots)}
                                        />
                                        <SelectInput
                                            value={selectedTime}
                                            onChange={(e) => setSelectedTime(e.target.value)}
                                            firstOption={"Select a time"}
                                            options={freeSlots[selectedDate]}
                                            isDisabled={selectedDate.length === 0}
                                        />
                                    </>
                                )
                            }
                        </div>

                        {
                            error && (
                                <p className="text-red-500 text-md font-medium text-center">
                                    {error}
                                </p>
                            )
                        }
                        {
                            meetingScheduled ? (
                                <div className="my-12 text-center">
                                    <h2 className="text-2xl font-bold text-dark-blue mb-4">
                                        Your free consultation is scheduled! 🎉
                                    </h2>
                                    <p className="text-lg font-medium text-black/80 max-w-xl mx-auto">
                                        Completing the form was the <span className="font-semibold text-dark-blue">first step toward progress and growth</span>.
                                        Check your <span className="font-semibold text-dark-blue">email for the Zoom link</span> to continue your journey.
                                    </p>
                                </div>

                            ) : (
                                <button
                                    type="submit"
                                    disabled={loadingSubmitForm}
                                    className="disabled:cursor-not-allowed disabled:opacity-60 disabled:shadow-none
                                    hover:shadow-md hover:shadow-dark-blue/60 transition-all duration-300 ease-in-out
                                    shadow-sm shadow-dark-blue/30 border-2 border-white/20
                                    w-full py-2 bg-dark-blue text-white text-lg font-semibold rounded-sm cursor-pointer"
                                >
                                    {
                                        loadingSubmitForm ? (
                                            <div className="flex justify-center gap-6 items-center">
                                                Booking...
                                                <div className="loader-white w-8 h-8"/>
                                            </div>
                                        ) : "Book Session"
                                    }
                                </button>
                            )
                        }
                    </form>
                </div>
            </section>
        </main>
    )
}

function TextInput({label, name, placeholder, onChange}: {
    label: string;
    name: string;
    placeholder: string;
    onChange: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
}) {
    return (
        <label className="flex flex-col gap-2">
            <span className="text-black font-semibold text-xl"><sup>*</sup> {label}</span>
            <textarea
                onChange={onChange}
                rows={2}
                name={name}
                placeholder={placeholder}
                className="bg-dark-blue/5 border-1 border-black/10 py-1 px-2 rounded-sm
                font-medium resize-none overflow-hidden min-h-fit"
            />
        </label>
    )
}

function SelectInput({value, onChange, firstOption, options, isDisabled}: {
    value: string;
    onChange: (e: React.ChangeEvent<HTMLSelectElement>) => void;
    firstOption: string;
    options: string[];
    isDisabled?: boolean;
}) {
    return (
        <select
            className="disabled:opacity-20 disabled:cursor-not-allowed
            bg-dark-blue/5 border-1 border-black/10 py-2 px-8 rounded-sm
                font-medium resize-none overflow-hidden min-h-fit cursor-pointer"
            disabled={isDisabled}
            value={value} onChange={onChange}>
            <option value="">{firstOption}</option>
            {options?.map((option) => (
                <option key={option} value={option}>
                    {option}
                </option>
            ))}
        </select>
    );
}


function Step({title, description, isLast}: {
    title: string;
    description: string;
    isLast?: boolean;
}) {
    return (
        <li className={`${isLast ? 'border-transparent' : ''}
        relative pl-6 flex gap-4 items-start border-l-4 border-dark-blue/10`}>
            <div
                style={{transform: 'translateX(calc(-0.75rem - 2px))'}}
                className="absolute left-0 top-0 h-[1.5rem] aspect-square bg-dark-blue rounded-full"/>
            <div className="pb-12">
                <h3 className="text-xl font-bold text-dark-blue">{title}</h3>
                <p className="text-black/80">{description}</p>
            </div>
        </li>
    )
}

export default FreeConsultation;