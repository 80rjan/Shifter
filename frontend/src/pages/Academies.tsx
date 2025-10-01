import {Instagram, Linkedin, Mail, MapPin} from "lucide-react"
import React from "react";
import {useAuthContext} from "../context/AuthContext.tsx";
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import Arrow from "../../public/Shifter-Arrow-White.png"
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import ShifterLogo from "../../public/Shifter-S2W-Transparent.png";
import NavbarLink from "../components/NavbarLink.tsx";
import {sendEmailApi} from "../api/contactApi.ts";

function Academies() {
    const {user, accessToken} = useAuthContext();
    const [subject, setSubject] = React.useState("");
    const [message, setMessage] = React.useState("");
    const [loading, setLoading] = React.useState(false);

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        // Handle form submission logic here
        console.log("Subject:", subject);
        console.log("Message:", message);

        setLoading(true);
        sendEmailApi(accessToken || "", "Mentoring: " + subject, message)
            .then(() => {
                console.log("Successfully sent email");
            })
            .catch((err) => {
                console.error("Error sending email:", err);
            })
            .finally(() => setLoading(false));
    }

    return (
        <main className="bg-gradient-to-b from-shifter via-shifter/20 via-40% to-beige">
            {/*Hero*/}
            <section className="flex flex-col items-center gap-4 w-full pb-60 pt-top-nav-lg px-horizontal-lg
                text-white-text">
                <h1 className="text-5xl font-bold">Transform Your Team's Expertise with Corporate Group Academies & Training</h1>
                <p className="text-xl font-light">
                    Transform your team into a synchronized unit of high-performers.
                    Our Group Academies deliver <strong className="font-bold">standardized, expert-led training</strong> to rapidly upskill entire departments,
                    close knowledge gaps, and drive consistent execution across your organization.
                    Inquire now to secure a personalized curriculum review for your business.
                </p>
            </section>


            {/*Contact Form*/}
            <section className=" flex items-center justify-center w-full px-horizontal-lg">
                <div className="relative -top-40 grid grid-cols-3 gap-x-20 rounded-lg bg-white p-4
                    shadow-md shadow-black/10">

                    {/*Contact Info*/}
                    <div className="border-1 border-white/40
                    relative overflow-clip col-span-1 flex flex-col gap-8 py-8 px-8 rounded-lg bg-shifter text-white">
                        <div className="flex flex-col gap-2 items-start text-left">
                            <h2 className="text-2xl font-semibold whitespace-nowrap">Contact Information</h2>
                            <p className="text-white/80 font-light text-sm">
                                Complete the form and our team will respond shortly to help you take the next step.
                            </p>
                        </div>
                        <div className="flex gap-4 items-center">
                            <Mail size={24} color="var(--color-white)" />
                            contact@shift-er.com
                        </div>
                        <div className="flex gap-4 items-center">
                            <MapPin size={24} color="var(--color-white)" />
                            Skopje, N. Macedonia
                        </div>

                        <div className="absolute -bottom-26 right-12 flex flex-col opacity-40 rotate-40">
                            <img src={Arrow} className="w-28 h-auto" alt="Shifter Arrow" />
                            <img src={Arrow} className="w-28 h-auto rotate-180" alt="Shifter Arrow" />
                        </div>
                    </div>

                    {/*Form*/}
                    <div className="flex flex-col gap-4 col-span-2">
                        <div className="flex justify-betwee gap-20 items-center">
                            <p className="font-light text-black-text/60 text-md whitespace-nowrap ">Name: <span
                                className="text-black-text font-normal">{user?.name}</span></p>
                            <p className="font-light text-black-text/60 text-md whitespace-nowrap ">Email: <span
                                className="text-black-text font-normal">{user?.email}</span></p>
                            <p className="text-black/40 text-sm col-span-2">
                                These values are automatically populated from your profile.
                                If any of them are incorrect, please update them in the Profile page.
                            </p>
                        </div>

                        <hr className="border-t-2 border-black/20"/>

                        <form
                            onSubmit={handleSubmit}
                            className="flex flex-col gap-4 items-start">
                            <TextInput
                                label="Your Subject"
                                name="subject"
                                placeholder="Enter the subject of your message"
                                rows={1}
                                onChange={(e) => setSubject(e.target.value)}
                            />
                            <TextInput
                                label="Message"
                                name="message"
                                placeholder="Write your message here..."
                                rows={8}
                                onChange={(e) => setMessage(e.target.value)}
                            />
                            <div className="flex items-center gap-6">
                                <button
                                    className="hover:shadow-shifter/40 transition-all duration-200 ease-in-out
                                    disabled:cursor-not-allowed disabled:opacity-60
                                    shadow-md shadow-shifter/20 bg-shifter text-white py-2 px-6 rounded-md cursor-pointer"
                                    disabled={loading}
                                    type="submit">
                                    {loading ? "Sending..." : "Send Message"}
                                </button>
                                {
                                    loading && <div className="loader" />
                                }
                            </div>
                        </form>
                    </div>
                </div>
            </section>

            <footer className="flex justify-between px-horizontal-lg py-vertical-sm ">
                <img src={ShifterLogo} alt="Shifter - Business Consulting, Mentoring & Online Courses Logo"
                     className="h-12"
                />

                <div className="flex gap-4 items-center">
                    <NavbarLink to={"/mentoring"} label={"Mentoring"} className="text-sm"/>
                    <NavbarLink to={"/consulting"} label={"Consulting"} className="text-sm"/>
                    <NavbarLink to={"/courses"} label={"Courses"} className="text-sm"/>
                    <NavbarLink to={"/academies"} label={"Academies"} className="text-sm"/>
                </div>

                <div className="flex gap-4 ">
                    <a
                        href="http://www.google.com"
                        target="_blank"
                        rel="noopener noreferrer"
                        className="bg-shifter/10 rounded-full flex items-center justify-center aspect-square scale-80"
                    >
                        <Linkedin size={20} color="var(--color-shifter)"/>
                    </a>

                    <a
                        href="http://www.google.com"
                        target="_blank"
                        rel="noopener noreferrer"
                        className="bg-shifter/10 rounded-full flex items-center justify-center aspect-square scale-80"
                    >
                        <Instagram size={20} color="var(--color-shifter)"/>
                    </a>
                </div>
            </footer>
        </main>
    )
}


function TextInput({label, name, placeholder, rows, onChange}: {
    label: string;
    name: string;
    placeholder: string;
    rows: number;
    onChange: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
}) {
    return (
        <label className="w-full flex flex-col items-start gap-2">
            <span className="text-black/40 font-semibold text-md peer-focused:text-shifter">{label}</span>
            <textarea
                onChange={onChange}
                rows={rows}
                name={name}
                className="peer w-full bg-dark-blue/5 border-1 border-black/10 py-1 px-2 rounded-sm
                resize-none min-h-fit custom-scrollbar
                focus:outline-none focus:border-shifter/40 focus:border-2"
                placeholder={placeholder}
            />
        </label>
    )
}

export default Academies