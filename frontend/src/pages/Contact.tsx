import { Mail, MapPin } from "lucide-react";
import React from "react";
import { useTranslation } from "react-i18next";
import { useAuthContext } from "../context/AuthContext.tsx";

import Arrow from "../../public/Shifter-Arrow-White.png";
import { sendEmailApi } from "../api/contactApi.ts";
import {useUserContext} from "../context/UserContext.tsx";

function Contact() {
    const { t } = useTranslation("contact");
    const { accessToken } = useAuthContext();
    const { user } = useUserContext();
    const [subject, setSubject] = React.useState("");
    const [message, setMessage] = React.useState("");
    const [loading, setLoading] = React.useState(false);
    const [error, setError] = React.useState("");
    const [isMessageSent, setIsMessageSent] = React.useState(false);

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        setLoading(true);
        sendEmailApi(accessToken || "", subject, message)
            .then(() => {
                setIsMessageSent(true);
                setError("");
            })
            .catch((err) => {
                setError(t("form.sendingError"));
                console.error("Error sending email for contact form:", err);
            })
            .finally(() => setLoading(false));
    };

    return (
        <main className="flex flex-col gap-between bg-gradient-to-b from-shifter via-shifter/20 via-40% to-main">
            {/* Hero */}
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

            {/* Contact Form */}
            <section className="flex items-center justify-center w-full px-horizontal pb-vertical">
                <div
                    className="relative flex flex-col gap-8 rounded-lg bg-white p-4 shadow-md shadow-black/10 w-full max-w-screen-2xl mx-auto
                        sm:gap-10 sm:p-6
                        md:gap-12
                        lg:grid lg:grid-cols-3 lg:gap-x-12
                        xl:gap-x-20">
                    {/* Contact Info */}
                    <div
                        className="border-1 border-white/40 relative overflow-clip flex flex-col gap-5 py-8 px-6 rounded-lg bg-shifter text-white
                            sm:gap-6 sm:py-10 sm:px-8
                            lg:col-span-1 lg:gap-8">
                        <div className="flex flex-col gap-3 items-start text-left">
                            <h2 className="text-xl font-semibold whitespace-nowrap
                                sm:text-2xl">{t("info.title")}</h2>
                            <p className="text-white/80 font-light text-sm leading-relaxed
                                sm:text-base
                                md:text-lg
                                2xl:text-xl">{t("info.description")}</p>
                        </div>
                        <div className="flex gap-4 items-center text-sm text-left
                            sm:gap-4 sm:text-base
                            md:text-lg
                            2xl:text-xl">
                            <Mail className="w-5 h-5 sm:w-6 sm:h-6 flex-shrink-0" color="var(--color-white)" />
                            <span className="break-all">{t("info.email")}</span>
                        </div>
                        <div className="flex gap-4 items-center text-sm text-left
                            sm:gap-4 sm:text-base
                            md:text-lg
                            2xl:text-xl">
                            <MapPin className="w-5 h-5 sm:w-6 sm:h-6 flex-shrink-0" color="var(--color-white)" />
                            <span>{t("info.location")}</span>
                        </div>
                        <div className="absolute -bottom-20 right-8 flex flex-col opacity-40 rotate-40
                            sm:-bottom-26 sm:right-12
                            lg:-bottom-26 lg:right-12">
                            <img src={Arrow} className="w-20 h-auto sm:w-28" alt="Shifter Arrow" />
                            <img src={Arrow} className="w-20 h-auto sm:w-28 rotate-180" alt="Shifter Arrow" />
                        </div>
                    </div>

                    {/* Form */}
                    <div className="flex flex-col gap-6 flex-1
                        sm:gap-7
                        lg:col-span-2 lg:py-6">
                        <div className="flex flex-col gap-3
                            sm:grid sm:grid-cols-2 sm:gap-x-6 sm:gap-y-2 sm:items-center">
                            <p className="text-black/40 text-xs col-span-2 mb-1
                                md:text-sm
                                2xl:text-base">{t("profileNotice")}</p>
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

                        <hr className="border-t-1 border-black/20 rounded-full my-2" />

                        <form onSubmit={handleSubmit} className="flex flex-col gap-6 items-start
                            sm:gap-7">
                            <div className="flex flex-col gap-7 items-center w-full
                                sm:gap-8">
                                <TextInput
                                    label={t("form.subject")}
                                    name="subject"
                                    placeholder={t("form.subjectPlaceholder")}
                                    rows={1}
                                    onChange={(e) => setSubject(e.target.value)}
                                />
                                <TextInput
                                    label={t("form.message")}
                                    name="message"
                                    placeholder={t("form.messagePlaceholder")}
                                    rows={8}
                                    onChange={(e) => setMessage(e.target.value)}
                                />
                                {error && <p className="text-red-500 text-sm font-medium text-center mt-2
                                    sm:text-md">{error}</p>}
                            </div>

                            {isMessageSent ? (
                                <div className="text-center w-full mt-4">
                                    <h2 className="text-lg font-bold text-shifter mb-3
                                        sm:text-xl">{t("form.successTitle")}</h2>
                                    <p className="text-sm font-medium text-black/80 max-w-xl mx-auto leading-relaxed
                                        sm:text-md">
                                        {t("form.successDescription")}
                                    </p>
                                </div>
                            ) : (
                                <div className="flex flex-col items-center gap-4 w-full mt-6
                                    sm:flex-row sm:gap-6">
                                    <button
                                        className="hover:shadow-shifter/60 transition-all duration-200 ease-in-out disabled:cursor-not-allowed disabled:opacity-60 shadow-md shadow-shifter/40 bg-shifter text-white rounded-md cursor-pointer w-full
                                        text-sm font-medium py-2.5
                                        md:text-base
                                        2xl:text-lg 2xl:py-3"
                                        disabled={loading}
                                        type="submit"
                                    >
                                        {loading ? t("form.sending") : t("form.sendButton")}
                                    </button>
                                    {loading && <div className="loader" />}
                                </div>
                            )}
                        </form>
                    </div>
                </div>
            </section>
        </main>
    );
}

function TextInput({ label, name, placeholder, rows, onChange }: { label: string; name: string; placeholder: string; rows: number; onChange: (e: React.ChangeEvent<HTMLTextAreaElement>) => void }) {
    return (
        <label className="w-full flex flex-col items-start gap-1 text-black-text
            sm:gap-2">
            <span className="text-black/40 font-semibold text-sm peer-focused:text-shifter
                sm:text-md">{label}</span>
            <textarea
                required
                onChange={onChange}
                rows={rows}
                name={name}
                className="peer w-full bg-dark-blue/5 border-1 border-black/10 py-1 px-2 rounded-sm resize-none min-h-fit custom-scrollbar focus:outline-none focus:border-shifter/40 focus:border-2 text-sm
                    sm:text-base"
                placeholder={placeholder}
            />
        </label>
    );
}

export default Contact;
