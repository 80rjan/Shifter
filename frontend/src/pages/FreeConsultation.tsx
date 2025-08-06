import {useAuthContext} from "../context/AuthContext.tsx";

function FreeConsultation() {
    const {user} = useAuthContext();

    return (
        <main className="">
            {/*HERO*/}
            <section
                style={{paddingTop: 'calc(var(--spacing-top-nav-lg) + 4rem)'}}
                className="bg-dark-blue text-white w-full px-horizontal-lg py-vertical-lg pt-top-nav-lg text-left">
                <div className="flex flex-col gap-4 w-1/2">
                    <h1 className="text-5xl font-bold">Book Your Free Expert Session</h1>
                    <p className="text-xl font-light ">
                        Talk to an expert about your business goals and challenges. Get a personalized mentorship
                        plan or course recommendation that fits your unique situation.
                    </p>
                </div>
            </section>

            <section className="bg-dark-blue/5 flex gap-20 py-vertical-lg px-horizontal-lg text-left">
                {/*STEPS*/}
                <div className="flex flex-col gap-12 w-1/3">
                    <h2 className="text-dark-blue font-bold text-5xl">Next Steps After Submission?</h2>

                    <div>
                        <ol className="flex flex-col">
                            <Step
                                title={"Expert Prepares and Sends Invite"}
                                description={"Our expert reviews your request and sends a meeting invitation to your email."}
                            />
                            <Step
                                title={"You Confirm the Invite"}
                                description={"You’ll receive an email with the meeting details — simply confirm the invite to secure your spot."}
                            />
                            <Step
                                title={"Join Your Online Session"}
                                description={"Attend the session and walk away with a tailored plan built around your goals — designed to help you overcome challenges, grow, and move forward with clarity."}
                                isLast={true}
                            />
                        </ol>
                    </div>
                </div>

                {/*FORM*/}
                <div className="flex flex-col gap-4 bg-white rounded-xl w-2/3 px-horizontal-sm py-vertical-md ">
                    {/*Automatically populated*/}
                    <div className="grid grid-cols-2 gap-y-6">
                        <p className="font-light text-black/60 text-lg ">Name: <span
                            className="text-black font-medium">{user?.name}</span></p>
                        <p className="font-light text-black/60 text-lg ">Email: <span
                            className="text-black font-medium">{user?.email}</span></p>
                        <p className="font-light text-black/60 text-lg ">Company Type: <span
                            className="text-black font-medium">{user?.companyType.toLowerCase().split("_").map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(" ")}</span>
                        </p>
                        <p className="font-light text-black/60 text-lg ">Work Position: <span
                            className="text-black font-medium">{user?.workPosition}</span></p>
                        <p className="text-black/40 col-span-2">
                            <sup>*</sup> These values are automatically populated from your profile.
                            If any of them are incorrect, please update them in the Profile page.
                        </p>
                    </div>

                    <hr className="border-t-2 border-black/20"/>

                    {/*Form*/}
                    <form
                        className="flex flex-col gap-6 w-full">
                        <LabelInput
                            label="About your business"
                            name="intro"
                            placeholder="What you do, industry, customers"
                        />
                        <LabelInput
                            label="Your current challenges"
                            name="challenges"
                            placeholder="E.g. sales, growth, team issues"
                        />
                        <LabelInput
                            label="What you want from the session"
                            name="expectations"
                            placeholder="Advice, strategy, solutions"
                        />
                        <LabelInput
                            label="Anything else? (optional)"
                            name="additional"
                            placeholder="Extra context or details"
                        />

                    </form>
                </div>
            </section>
        </main>
    )
}

function LabelInput({label, name, placeholder}: {
    label: string;
    name: string;
    placeholder: string;
}) {
    return (
        <label className="flex flex-col gap-2">
            <span className="text-black font-semibold text-xl">{label}</span>
            <textarea
                rows={2}
                name={name}
                placeholder={placeholder}
                className="bg-dark-blue/5 border-1 border-black/10 py-1 px-2 rounded-sm
                font-medium resize-none overflow-hidden min-h-fit"
            />
        </label>
    )
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